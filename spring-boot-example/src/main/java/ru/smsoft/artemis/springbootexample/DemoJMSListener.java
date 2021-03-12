package ru.smsoft.artemis.springbootexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import ru.smsoft.artemis.springbootexample.jaxb.v6_1.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.Instant;

@Component
public class DemoJMSListener {
    private static final Logger log = LoggerFactory.getLogger(DemoJMSListener.class);
    //Очередь для ответа
    @Value("${queue.output}")
    private String outputQueue;
    //Стандартный класс обертка-шаблон для работы с JMS в Spring
    //Можно использовать @Autowired, но spring рекомендует установку полей через конструктор
    private final JmsMessagingTemplate jmsMessagingTemplate;
    //Jaxb контекст для формата 61
    private final JAXBContext v61JaxbContext;

    public DemoJMSListener(JmsMessagingTemplate jmsMessagingTemplate) throws JAXBException {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
        this.v61JaxbContext = JAXBContext.newInstance(ObjectFactory.class);
    }

    /**
     * Метод слушает сообщения из очереди destination, параметр задается через конфигурацию
     * В теле можно кидать различные исключения, они будут обработаны автоматически и если сообщению не удалось обработаться, то
     * если на сервере произведены настройки, оно упадёт в очередь ошибок
     * @param message - Сообщение целиком со всеми различными заголовками, в том числе и с JMS
     * @param jmsMessageHeaderAccessor - удобная обертка для доступа только для JMS заголовков, все эти заголовки есть и в исходном message
     * можно убрать этот параметр если не требуется удобного доступа
     */
    @JmsListener(destination = "${queue.input}")
    public void listener(Message<String> message, JmsMessageHeaderAccessor jmsMessageHeaderAccessor) throws JAXBException {
        long startTime = System.currentTimeMillis();
        //Получаем тело сообщения
        String payload = message.getPayload();
        log.info("Incoming message (id: {}): {}", payload, jmsMessageHeaderAccessor.getMessageId());
        //Пытаемся создать объект 61 из строки
        Unmarshaller unmarshaller = v61JaxbContext.createUnmarshaller();
        //Если преобразование не удастся, возникнет исключение и сообщение уйдет в очередь ошибок
        CoordinateMessage coordinateMessage = (CoordinateMessage) unmarshaller.unmarshal(new StringReader(payload));
        //Если всё успешно выводим в лог выдержку из сообщения и формируем статус для отправки
        log.info("Message headers: ");
        message.getHeaders().forEach((k,v) -> {
            log.info("{}: {}", k,v);
        });
        log.info("Service number: {}", coordinateMessage.getCoordinateDataMessage().getService().getServiceNumber());
        //Генерируем статус и отправляем его в очередь ответов
        CoordinateStatusMessage statusMessage = createStatus1040(coordinateMessage.getCoordinateDataMessage().getService().getServiceNumber());
        //Преобразовываем объект в xml строку
        Marshaller marshaller = v61JaxbContext.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.marshal(statusMessage, sw);
        String statusXml = sw.toString();
        log.info("Status XML: {}", statusXml);
        jmsMessagingTemplate.send(outputQueue, MessageBuilder.withPayload(statusXml).build());
        long endTime = System.currentTimeMillis();
        log.info("Message processed by {} ms", endTime - startTime);
    }

    private CoordinateStatusMessage createStatus1040(String serviceNumber) {
        ObjectFactory objectFactory = new ObjectFactory();
        CoordinateStatusMessage coordinateStatusMessage = new CoordinateStatusMessage();
        CoordinateStatusData coordinateStatusData = new CoordinateStatusData();
        Department department = new Department();
        department.setInn(objectFactory.createDepartmentInn("7710878000"));
        department.setOgrn(objectFactory.createDepartmentOgrn("1107746943347"));
        department.setName(objectFactory.createBaseDepartmentTypeName("Департамент информационных технологий города Москвы"));
        department.setCode("8899");
        coordinateStatusData.setDepartment(department);
        Person person = new Person();
        person.setFirstName("Демо пример Artemis spring boot");
        person.setLastName("Демо пример Artemis spring boot");
        person.setMiddleName("Демо пример Artemis spring boot");
        person.setJobTitle("Демо пример Artemis spring boot");
        coordinateStatusData.setResponsible(person);
        coordinateStatusData.setNote("Заявка успешно получена");
        coordinateStatusData.setServiceNumber(serviceNumber);
        coordinateStatusData.setResponseDate(objectFactory.createCoordinateStatusDataResponseDate(DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(Instant.now().toString())));
        StatusType2 status = new StatusType2();
        status.setStatusCode(1040);
        status.setStatusTitle("Заявка обработана");
        status.setStatusDate(DatatypeFactory.newDefaultInstance().newXMLGregorianCalendar(Instant.now().toString()));
        coordinateStatusData.setStatus(status);
        coordinateStatusMessage.setCoordinateStatusDataMessage(coordinateStatusData);
        return coordinateStatusMessage;
    }
}
