import RPi.GPIO as gpio
import time


class SevenSeg:

    def __init__(self):
        gpio.setmode(gpio.BCM)
        gpio.setwarnings(False)

        gpio.setup(self.write, gpio.OUT)
        gpio.setup(self.mode, gpio.OUT)
        gpio.setup(self.id0, gpio.OUT)
        gpio.setup(self.id1, gpio.OUT)
        gpio.setup(self.id2, gpio.OUT)
        gpio.setup(self.id3, gpio.OUT)
        gpio.setup(self.id4_shutdown, gpio.OUT)
        gpio.setup(self.id5_decode, gpio.OUT)
        gpio.setup(self.id6_hexa_codeb, gpio.OUT)
        gpio.setup(self.id7_data_coming, gpio.OUT)


    id0 = 3
    id1 = 4
    id2 = 2
    id3 = 11
    id4_shutdown = 25
    id5_decode = 27
    id6_hexa_codeb = 18
    id7_data_coming = 22
    write = 23
    mode = 24

    write_delay = 0.02
    small_delay = 0.007

    def get_as_binary(self, number):
        bin = "{0:b}".format(number)
        bin = bin.rjust(4, "0")
        print bin

        data = []

        for c in bin:
            data.append(int(c))

        return data

    def write_number(self, n):

        gpio.output(self.mode, 1)
        time.sleep(self.write_delay)

        gpio.output(self.write, 0)

        gpio.output(self.id4_shutdown, 1)
        gpio.output(self.id5_decode, 0)
        gpio.output(self.id6_hexa_codeb, 1)
        gpio.output(self.id7_data_coming, 0)

        time.sleep(self.write_delay)
        gpio.output(self.write, 1)

        time.sleep(self.write_delay)

        gpio.output(self.mode, 0)

        data = self.get_as_binary(n)
        for i in range(0, 8):
            gpio.output(self.write, 0)
            gpio.output(self.id0, 1)#data[0])
            gpio.output(self.id1, 1)#data[1])
            gpio.output(self.id2, 1)#data[2])
            gpio.output(self.id3, 1)#data[3])
            gpio.output(self.id4_shutdown, 1)
            gpio.output(self.id5_decode, 1)
            gpio.output(self.id6_hexa_codeb, 1)
            gpio.output(self.id7_data_coming, 1)
            time.sleep(self.write_delay)
            gpio.output(self.write, 1)

    def count(self):
        print "Counting"
        for i in range(0, 10):
            self.write_number(i)
            # time.sleep(1)

if __name__ == '__main__':

    print "Doing..."

    seven_seg = SevenSeg()

    k = 0
    while True:
        print k
        seven_seg.write_number(k)
        time.sleep(1)
        k += 1
        if k > 9:
            k = 0

    print "Done!"
