#include <HX711.h>
#include <SoftwareSerial.h>
#define RX_PIN 6
#define TX_PIN 5
#define DT_PIN A1
#define SCK_PIN A2
#define sampleTime 5  // number of get_unit(10) datas collected to calculate standard deviation
#define stdDevThreshold 1 // if the standard deviation of datas[10] is larger than this value, run the loop again
// HX711.DOUT	- pin #A1
// HX711.PD_SCK	- pin #A0

HX711 scale(DT_PIN, SCK_PIN);		// parameter "gain" is ommited; the default value 128 is used by the library
SoftwareSerial BT(TX_PIN, RX_PIN);
float previous, latter;   //for comparison to decide whether to send the data
float delta;
float datas[sampleTime];
void setup() {
  Serial.begin(38400);
  //while(!Serial);
  BT.begin(9600);

  Serial.println("HX711 Demo");

  Serial.println("Before setting up the scale:");
  Serial.print("read: \t\t");
  Serial.println(scale.read());			// print a raw reading from the ADC

  Serial.print("read average: \t\t");
  Serial.println(scale.read_average(20));  	// print the average of 20 readings from the ADC

  Serial.print("get value: \t\t");
  Serial.println(scale.get_value(5));		// print the average of 5 readings from the ADC minus the tare weight (not set yet)

  Serial.print("get units: \t\t");
  Serial.println(scale.get_units(5), 1);	// print the average of 5 readings from the ADC minus tare weight (not set) divided
  // by the SCALE parameter (not set yet)

  scale.set_scale(2280.f);                      // this value is obtained by calibrating the scale with known weights; see the README for details
  scale.tare();				        // reset the scale to 0

  Serial.println("After setting up the scale:");

  Serial.print("read: \t\t");
  Serial.println(scale.read());                 // print a raw reading from the ADC

  Serial.print("read average: \t\t");
  Serial.println(scale.read_average(20));       // print the average of 20 readings from the ADC

  Serial.print("get value: \t\t");
  Serial.println(scale.get_value(5));		// print the average of 5 readings from the ADC minus the tare weight, set with tare()

  Serial.print("get units: \t\t");
  Serial.println(scale.get_units(5), 1);        // print the average of 5 readings from the ADC minus tare weight, divided
  // by the SCALE parameter set with set_scale

  Serial.println("Readings:");
  for (uint8_t i = 0; i < sampleTime; i++) {
    datas[i] = scale.get_units(5);
  }
  while (stdDev(datas, sampleTime) > stdDevThreshold) {
    Serial.println("deviation too high");
    delay(1000);
  }
  previous = latter = average(datas, sampleTime);
}

void loop() {
  initialize();
  Serial.print("one reading:\t");
  Serial.println(convert(scale.get_units()), 1);
  //  Serial.print("\t| average:\t");
  //  Serial.println(scale.get_units(10), 1);

  for (uint8_t i = 0; i < sampleTime; i++) {
    datas[i] = convert(scale.get_units(5));
  }
  float deviation = stdDev(datas, sampleTime);
  if (deviation < stdDevThreshold) {
    Serial.print("deviation is : ");
    Serial.println(deviation);
    latter = average(datas, sampleTime);
    // if water is consumed
    Serial.print("previous is : ")	;
    Serial.println(previous);
    Serial.print("latter is : ");
    Serial.println(latter);
    delta = previous - latter;
    Serial.print("delta is :");
    Serial.println(delta);
    if (delta > 10 && latter > -30 ) {
      //while(!BT.available());
      Serial.println("Connected!");
      String deltaStr = String(delta);
      BT.write(("\t" + deltaStr).c_str());
    }
    if(latter > 0) previous = latter;
    latter = -50;
  }

  //  previous = latter;
  //  latter = -50;
  scale.power_down();
  // put the ADC in sleep mode
  delay(1000);
  scale.power_up();
}

void initialize() {
  for (uint8_t i = 0; i < sampleTime; i++) {
    datas[i] = NULL;
  }
}

float convert(float x) {
  return 42.74 * x;
}
float stdDev(float * data, uint8_t length) {
  float squaredSum = 0;
  float sum = 0;
  for (uint8_t i = 0; i < length; i++) {
    squaredSum += data[i] * data[i];
    sum += data[i];
  }
  return sqrt(squaredSum / length - (sum / length) * (sum / length));
}

float average(float * data, uint8_t length) {
  float sum = 0;
  for (uint8_t i = 0; i < length; i++) {
    sum += datas[i];
  }
  return sum / length;
}
