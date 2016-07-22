import RPi.GPIO as gpio


class Zapper:

    pin = 0
    zap_callback = None


    def __init__(self, pin):
        self.pin = pin
        gpio.setmode(gpio.BCM)
        gpio.setup(pin, gpio.IN)

    def on_zap(self, pin):
        if self.zap_callback is not None:
            self.zap_callback(not gpio.input(pin))

    def is_zapped(self):
        return not gpio.input(self.pin)

    def wait_for_zap(self):
        while not self.is_zapped():
            continue

    def set_callback(self, callback):
        self.zap_callback = callback
        gpio.add_event_detect(self.pin, gpio.BOTH, callback=self.on_zap, bouncetime=300)
