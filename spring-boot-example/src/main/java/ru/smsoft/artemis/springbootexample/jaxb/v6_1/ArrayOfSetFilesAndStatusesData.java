//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.21 at 08:15:35 PM MSK 
//


package ru.smsoft.artemis.springbootexample.jaxb.v6_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfSetFilesAndStatusesData complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfSetFilesAndStatusesData"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SetFilesAndStatusesData" type="{http://asguf.mos.ru/rkis_gu/coordinate/v6_1/}SetFilesAndStatusesData" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSetFilesAndStatusesData", propOrder = {
    "setFilesAndStatusesData"
})
public class ArrayOfSetFilesAndStatusesData {

    @XmlElement(name = "SetFilesAndStatusesData", nillable = true)
    protected List<SetFilesAndStatusesData> setFilesAndStatusesData;

    /**
     * Gets the value of the setFilesAndStatusesData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the setFilesAndStatusesData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSetFilesAndStatusesData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SetFilesAndStatusesData }
     * 
     * 
     */
    public List<SetFilesAndStatusesData> getSetFilesAndStatusesData() {
        if (setFilesAndStatusesData == null) {
            setFilesAndStatusesData = new ArrayList<SetFilesAndStatusesData>();
        }
        return this.setFilesAndStatusesData;
    }

}
