package lab2;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class Main {


    Timer timer;
    int seconds = 4;

    DateTimeFormatter Date = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();

    String topic = "KYH/ME/Tempsensor";
    String content = Date.format(now) + " Temperate : " + (int) (Math.random() * 11 + 15) + " °C";
    int qos = 2;
    String broker = "tcp://broker.hivemq.com:1883";
    String clientId = "JavaSample";
    MemoryPersistence persistence = new MemoryPersistence();

   Main() {
       timer = new Timer();
       timer.schedule(new RemindTask(), seconds * 1000L);
            try {
                MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
                MqttConnectOptions connOpts = new MqttConnectOptions();
                connOpts.setCleanSession(true);
                System.out.println("Connecting to broker: " + broker);
                sampleClient.connect(connOpts);
                System.out.println("Connected");
                System.out.println("Publishing message: " + content);
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }



         class RemindTask extends TimerTask {
             @Override
             public void run() {
                 System.out.println("Temperaturen uppdateras efter 1 minut ");
                 timer.cancel();
                 timer = new Timer();
                 new Main();
             }
         }

         public static void main(String[] args) {
             new Main();

         }
     }











