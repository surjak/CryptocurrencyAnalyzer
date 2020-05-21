package config

object EmailSender {


  def sendEmail(emailAddress: String, message: String) = {

    import org.apache.commons.mail.{DefaultAuthenticator, SimpleEmail}
    val email = new SimpleEmail
    email.setHostName("smtp.googlemail.com")
    email.setSmtpPort(465)
    email.setAuthenticator(new DefaultAuthenticator("alertappagh@gmail.com", "Alert321"))
    email.setSSLOnConnect(true)
    email.setFrom("alertappagh@gmail.com")
    email.setSubject("Email - Alert")
    email.setMsg(message)
    email.addTo(emailAddress)
    email.send()


  }
}
