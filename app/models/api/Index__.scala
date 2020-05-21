package models.api

import java.util.HashMap
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
@JsonPropertyOrder(Array("name", "value", "level", "description", "advice", "color"))
class Index__ {

  @JsonProperty("name")
  @BeanProperty
  var name: String = _

  @JsonProperty("value")
  @BeanProperty
  var value: java.lang.Integer = _

  @JsonProperty("level")
  @BeanProperty
  var level: String = _

  @JsonProperty("description")
  @BeanProperty
  var description: String = _

  @JsonProperty("advice")
  @BeanProperty
  var advice: String = _

  @JsonProperty("color")
  @BeanProperty
  var color: String = _

  @JsonIgnore
  @BeanProperty
  var additionalProperties: Map[String, Any] = new HashMap[String, Any]()

  @JsonAnySetter
  def setAdditionalProperty(name: String, value: AnyRef) {
    this.additionalProperties.put(name, value)
  }
}
