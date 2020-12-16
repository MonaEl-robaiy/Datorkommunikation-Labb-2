package lab2;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class TempSensor {
    private MqttClient client;
    private final String topic = "KYH/ME/Tempsensor";
    private final String broker = "tcp://broker.hivemq.com:1883";
    private final String clientId = "kyh-mona-sens";
    private MemoryPersistence memoryPersistence;

    public static void main(String[] args) {
        TempSensor tempSensor = new TempSensor();

        tempSensor.run();
    }

    private void run(){
        connect();

        while(true){
            String t = measure();

            post(t);
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void post(String t) {
        try {
            client.publish(topic, t.getBytes(), 2, false);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private String measure() {
        return "20";
    }

    private void connect() {
        memoryPersistence = new MemoryPersistence();
        try {
            client = new MqttClient(broker, clientId, memoryPersistence);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        try {
            client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
