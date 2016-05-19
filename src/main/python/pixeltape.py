# NeoPixel library strandtest example
# Author: Tony DiCola (tony@tonydicola.com)
#
# Direct port of the Arduino NeoPixel library strandtest example.  Showcases
# various animations on a strip of NeoPixels.
import time

from neopixel import *

# LED strip configuration:
LED_COUNT = 4  # Number of LED pixels.
LED_PIN = 12  # GPIO pin connected to the pixels (must support PWM!).
LED_FREQ_HZ = 800000  # LED signal frequency in hertz (usually 800khz)
LED_DMA = 5  # DMA channel to use for generating signal (try 5)
LED_BRIGHTNESS = 255  # Set to 0 for darkest and 255 for brightest
LED_INVERT = False  # True to invert the signal (when using NPN transistor level shift)

# def pulse(strip, )

if __name__ == '__main__':
    strip = Adafruit_NeoPixel(LED_COUNT, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS)
    strip.begin()

    while True:
        strip.setPixelColor(0, Color(255, 255, 0))
        strip.setPixelColor(1, Color(0, 0, 255))
        strip.setPixelColor(2, Color(255, 0, 0))
        strip.setPixelColor(3, Color(0, 255, 0))
        for i in range(255):
            strip.setBrightness(i)
            strip.show()
            time.sleep(0.005)
        for i in range(255):
            strip.setBrightness(255-i)
            strip.show()
            time.sleep(0.005)
