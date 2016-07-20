import pixeltape
# import audioplayer

# def play_audio():
#     audio_player = audioplayer.AudioPlayer()
#     audio_player.play_file("../audio/atest.mp3")
#
#
# def count_display():
#     seven_seg = sevenseg.SevenSeg()
#     while True:
#         seven_seg.count()

tape = pixeltape.PixelTape()


def clear():
    tape.clear()


def background_pattern():
    tape.twinkle(50, 200, 15, 50, 100, 15, 50, 255, 20)


def travel_pattern():
    tape.theaterChase()


if __name__ == '__main__':

    print "Pixel Tape Active"

    tape.clear()

    try:
        while True:
            background_pattern()
            travel_pattern()

    except KeyboardInterrupt:
        clear()
        print "Shut Down Pixel Tape"

    # play_audio()

    # while True:
    #     pass

