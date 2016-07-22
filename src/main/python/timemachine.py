from zapper import Zapper
import RPi.GPIO as gpio
import pixeltape
import pygame
from neopixel import *
from threading import Thread
import time
from audioplayer import AudioPlayer
from pixeltape import PixelTape

zapper = Zapper(17)
tape = PixelTape()
audio = AudioPlayer()
years = []
durations = []


def time_travel(year=-1):
    if year == -1:
        yearname = ""
    else:
        yearname = str(year)

    audio.play("audio/timetravel"+yearname+".mp3")
    tape.time_travel_pattern()


def play_year(year, duration):
    audio.play("audio/"+str(year)+".mp3")
    tape.twinkle(duration, 50, 200, 15, 50, 100, 15, 50, 255, 20, 3)


def add_year(year, duration):
    global years, durations
    years.append(year)
    durations.append(duration)


def start():

    print "Starting Time Machine..."

    add_year(1969, 61)
    add_year(1987, 44)
    add_year(2009, 66)
    add_year(2015, 52)
    add_year(2031, 50)
    add_year(2054, 59)

    year_pointer = 0

    while True:
        year = years[year_pointer]
        duration = durations[year_pointer]

        tape.fade_in(1, 80, 25, 5)

        zapper.wait_for_zap()

        time_travel(year)

        play_year(year, duration)

        time_travel()

        year_pointer += 1
        if year_pointer >= len(years):
            year_pointer = 0

    print "Done"


if __name__ == '__main__':
    start()
