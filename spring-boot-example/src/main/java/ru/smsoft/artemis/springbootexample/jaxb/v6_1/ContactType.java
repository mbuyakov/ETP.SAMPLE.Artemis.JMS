//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.21 at 08:15:35 PM MSK 
//


package ru.smsoft.artemis.springbootexample.jaxb.v6_1;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ContactType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Declarant"/&gt;
 *     &lt;enumeration value="Trustee"/&gt;
 *     &lt;enumeration value="LegalRepresentative"/&gt;
 *     &lt;enumeration value="Contact"/&gt;
 *     &lt;enumeration value="Child"/&gt;
 *     &lt;enumeration value="Spouse"/&gt;
 *     &lt;enumeration value="Relative"/&gt;
 *     &lt;enumeration value="Other"/&gt;
 *     &lt;enumeration value="Father"/&gt;
 *     &lt;enumeration value="Mother"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ContactType")
@XmlEnum
public enum ContactType {


    /**
     * Заявитель
     * 
     */
    @XmlEnumValue("Declarant")
    DECLARANT("Declarant"),

    /**
     * Доверенное лицо
     * 
     */
    @XmlEnumValue("Trustee")
    TRUSTEE("Trustee"),

    /**
     * Законный представитель
     * 
     */
    @XmlEnumValue("LegalRepresentative")
    LEGAL_REPRESENTATIVE("LegalRepresentative"),

    /**
     * Контактное лицо
     * 
     */
    @XmlEnumValue("Contact")
    CONTACT("Contact"),

    /**
     * Ребенок
     * 
     */
    @XmlEnumValue("Child")
    CHILD("Child"),

    /**
     * Супруг(а)
     * 
     */
    @XmlEnumValue("Spouse")
    SPOUSE("Spouse"),

    /**
     * Родственник
     * 
     */
    @XmlEnumValue("Relative")
    RELATIVE("Relative"),

    /**
     * Другое
     * 
     */
    @XmlEnumValue("Other")
    OTHER("Other"),

    /**
     * Отец
     * 
     */
    @XmlEnumValue("Father")
    FATHER("Father"),

    /**
     * Мать
     * 
     */
    @XmlEnumValue("Mother")
    MOTHER("Mother");
    private final String value;

    ContactType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ContactType fromValue(String v) {
        for (ContactType c: ContactType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
