# NeoPixel library strandtest example
# Author: Tony DiCola (tony@tonydicola.com)
#
# Direct port of the Arduino NeoPixel library strandtest example.  Showcases
# various animations on a strip of NeoPixels.
import random
# import RPi.GPIO as gpio
import time
import os.path

from neopixel import *

# LED strip configuration:
TWINKLE_LED_COUNT = 29  # Number of LED pixels.
AMBIENT_LED_COUNT = 57  # Number of LED pixels.
LED_PIN = 18  # GPIO pin connected to the pixels (must support PWM!).
LED_FREQ_HZ = 800000  # LED signal frequency in hertz (usually 800khz)
LED_DMA = 5  # DMA channel to use for generating signal (try 5)
LED_BRIGHTNESS = 255  # Set to 0 for darkest and 255 for brightest
LED_INVERT = False  # True to invert the signal (when using NPN transistor level shift)

# COMM_PIN = 18

class PixelTape:

    strip = Adafruit_NeoPixel(TWINKLE_LED_COUNT+AMBIENT_LED_COUNT, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS)

    cancelPattern = False

    def __init__(self):
        self.strip.begin()

    def all_on(self, colour):
        print TWINKLE_LED_COUNT+AMBIENT_LED_COUNT
        for i in range(TWINKLE_LED_COUNT + AMBIENT_LED_COUNT):
            self.strip.setPixelColor(i, colour)

        self.strip.show()

    def clear(self):
        self.all_on(Color(0, 0, 0))

    def cancel(self):
        print "Cancel"
        self.cancelPattern = True

    def theaterChase(self, wait_ms=40, iterations=10):
        g = 255
        r = 255
        b = 100
        d = 50

        while os.path.isfile("timetravel"):
            for q in range(3):
                for i in range(0, self.strip.numPixels(), 3):
                    self.strip.setPixelColor(i+q, Color(g, r, b))
                self.strip.show()
                time.sleep(wait_ms/1000.0)
                for i in range(0, self.strip.numPixels(), 3):
                    self.strip.setPixelColor(i+q, 0)

            g = g + d
            b = b + d
            if g >= 255 or b >= 255:
                g = 255
                b = 100
                d = -d
            if g <= 0 or b <= 0:
                g = 0
                b = 0
                d = -d

    def twinkle(self, min_green, max_green, delta_g, min_red, max_red, delta_r, min_b, max_b, delta_b):
        green = []
        green_delta = []
        red = []
        red_delta = []
        blue = []
        blue_delta = []

        for i in range(self.strip.numPixels()):
            g = int(random.random() * (max_green - min_green)) + min_green
            r = int(random.random() * (max_red - min_red)) + min_red
            b = int(random.random() * (max_b - min_b)) + min_b
            green.append(g)
            red.append(r)
            blue.append(b)
            if random.random() < 0.5:
                green_delta.append(delta_g)
                red_delta.append(delta_r)
                blue_delta.append(delta_b)
            else:
                green_delta.append(-delta_g)
                red_delta.append(-delta_r)
                blue_delta.append(-delta_b)

            self.strip.setPixelColor(i, Color(g, r, b))
            self.strip.show()

        while not os.path.isfile("timetravel"):
            for i in range(self.strip.numPixels()):

                g = green[i] + green_delta[i]
                if g >= max_green:
                    g = max_green
                    green_delta[i] = -green_delta[i]
                elif g <= min_green:
                    g = min_green
                    green_delta[i] = -green_delta[i]
                green[i] = g

                r = red[i] + red_delta[i]
                if r >= max_red:
                    r = max_red
                    red_delta[i] = -red_delta[i]
                elif r <= min_red:
                    r = min_red
                    red_delta[i] = -red_delta[i]
                red[i] = r

                b = blue[i] + blue_delta[i]
                if b >= max_b:
                    b = max_b
                    blue_delta[i] = -blue_delta[i]
                elif b <= min_b:
                    b = min_b
                    blue_delta[i] = -blue_delta[i]
                blue[i] = b

                # print g, r, b
                self.strip.setPixelColor(i, Color(g, r, b))
                self.strip.show()

    def bound(self, i):
        if i < 0:
            return 0
        if i > 255:
            return 255
        return i
