package models.api

import java.util.HashMap
import java.util.List
import java.util.Map
import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import scala.beans.{BeanProperty, BooleanBeanProperty}
//remove if not needed
import scala.collection.JavaConversions._

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(Array("fromDateTime", "tillDateTime", "values", "indexes", "standards"))
class History {

  @JsonProperty("fromDateTime")
  @BeanProperty
  var fromDateTime: String = _

  @JsonProperty("tillDateTime")
  @BeanProperty
  var tillDateTime: String = _

  @JsonProperty("values")
  @BeanProperty
  var values: List[Value_] = null

  @JsonProperty("indexes")
  @BeanProperty
  var indexes: List[Index_] = null

  @JsonProperty("standards")
  @BeanProperty
  var standards: List[Standard_] = null

  @JsonIgnore
  @BeanProperty
  var additionalProperties: Map[String, Any] = new HashMap[String, Any]()

  @JsonAnySetter
  def setAdditionalProperty(name: String, value: AnyRef) {
    this.additionalProperties.put(name, value)
  }
}
