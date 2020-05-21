package config

object EmailSender {


  def sendEmail() = {

    import org.apache.commons.mail.{DefaultAuthenticator, SimpleEmail}
    val email = new SimpleEmail
    email.setHostName("smtp.googlemail.com")
    email.setSmtpPort(465)
    email.setAuthenticator(new DefaultAuthenticator("alertappagh@gmail.com", "Alert321"))
    email.setSSLOnConnect(true)
    email.setFrom("alertappagh@gmail.com")
    email.setSubject("TestMail")
    email.setMsg("This is a test mail ... :-)")
    email.addTo("alertappagh@gmail.com")
    email.send()


  }
}
