import RPi.GPIO as gpio
import time

id6 = 18
id5 = 27
id7 = 22
write = 23
mode = 24
id4 = 25
id1 = 4
id0 = 3
id2 = 2
id3 = 11


def setup():
    gpio.setmode(gpio.BCM)
    gpio.setwarnings(False)

    gpio.setup(id6, gpio.OUT)
    gpio.setup(id5, gpio.OUT)
    gpio.setup(id7, gpio.OUT)
    gpio.setup(write, gpio.OUT)
    gpio.setup(mode, gpio.OUT)
    gpio.setup(id4, gpio.OUT)
    gpio.setup(id1, gpio.OUT)
    gpio.setup(id0, gpio.OUT)
    gpio.setup(id2, gpio.OUT)
    gpio.setup(id3, gpio.OUT)


def control_word(type, decode, shutdown, coming):
    gpio.output(id6, type)
    gpio.output(id5, decode)
    gpio.output(id4, shutdown)
    gpio.output(id7, coming)


def write_word(dp, a, b, c, d, e, f, g):
    gpio.output(id7, dp)
    gpio.output(id6, a)
    gpio.output(id5, b)
    gpio.output(id4, c)
    gpio.output(id3, e)
    gpio.output(id2, g)
    gpio.output(id1, f)
    gpio.output(id0, d)


if __name__ == '__main__':

    print "Doing..."

    setup()

    while True:

        gpio.output(mode, 1)
        gpio.output(write, 1)
        control_word(0, 0, 0, 1)
        gpio.output(write, 0)
        gpio.output(mode, 0)

        for i in range(1, 8):
            gpio.output(write, 0)
            write_word(1, 0, 1, 0, 1, 0, 0, 1)
            gpio.output(write, 1)

    print "Done!"
