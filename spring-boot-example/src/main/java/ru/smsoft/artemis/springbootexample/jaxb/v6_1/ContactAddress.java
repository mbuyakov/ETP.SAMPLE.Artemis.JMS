//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.21 at 08:15:35 PM MSK 
//


package ru.smsoft.artemis.springbootexample.jaxb.v6_1;

import javax.xml.bind.annotation.*;


/**
 * Адрес контакта
 * 
 * <p>Java class for ContactAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactAddress"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://asguf.mos.ru/rkis_gu/coordinate/v6_1/}Address"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RegType" type="{http://asguf.mos.ru/rkis_gu/coordinate/v6_1/}RegType" minOccurs="0"/&gt;
 *         &lt;element name="Period" type="{http://asguf.mos.ru/rkis_gu/coordinate/v6_1/}Period" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactAddress", propOrder = {
    "regType",
    "period"
})
public class ContactAddress
    extends Address
{

    @XmlElement(name = "RegType")
    @XmlSchemaType(name = "string")
    protected RegType regType;
    @XmlElement(name = "Period")
    protected Period period;

    /**
     * Gets the value of the regType property.
     * 
     * @return
     *     possible object is
     *     {@link RegType }
     *     
     */
    public RegType getRegType() {
        return regType;
    }

    /**
     * Sets the value of the regType property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegType }
     *     
     */
    public void setRegType(RegType value) {
        this.regType = value;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link Period }
     *     
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link Period }
     *     
     */
    public void setPeriod(Period value) {
        this.period = value;
    }

}
