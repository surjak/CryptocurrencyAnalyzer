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
@JsonPropertyOrder(Array("name", "pollutant", "limit", "percent", "averaging"))
class Standard {

  @JsonProperty("name")
  @BeanProperty
  var name: String = _

  @JsonProperty("pollutant")
  @BeanProperty
  var pollutant: String = _

  @JsonProperty("limit")
  @BeanProperty
  var limit: java.lang.Integer = _

  @JsonProperty("percent")
  @BeanProperty
  var percent: java.lang.Integer = _

  @JsonProperty("averaging")
  @BeanProperty
  var averaging: String = _

  @JsonIgnore
  @BeanProperty
  var additionalProperties: Map[String, Any] = new HashMap[String, Any]()

  @JsonAnySetter
  def setAdditionalProperty(name: String, value: AnyRef) {
    this.additionalProperties.put(name, value)
  }
}
