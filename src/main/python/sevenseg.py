import RPi.GPIO as GPIO
import time

if __name__ == '__main__':

    on = False
    pin = 3

    while True:
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(3, GPIO.OUT)
        GPIO.output(3, on)
        print on
        on = not on
        time.sleep(1)
