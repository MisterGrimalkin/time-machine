import pygame


class AudioPlayer:

    def __init__(self):
        pygame.mixer.init()

    def play_file(self, filename):
        print "Playing " + filename
        pygame.mixer.music.load(filename)
        pygame.mixer.music.play()
        while pygame.mixer.music.get_busy():
            continue
