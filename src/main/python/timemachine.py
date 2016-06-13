import sevenseg
import thread
import audioplayer


def play_audio():
    audio_player = audioplayer.AudioPlayer()
    audio_player.play_file("aphex.mp3")


def count_display():
    seven_seg = sevenseg.SevenSeg()
    while True:
        seven_seg.count()


if __name__ == '__main__':

    print "Starting TimeMachine..."

    # thread.start_new_thread(play_audio, ())

    print "Starting Display..."

    # thread.start_new_thread(count_display, ())

    while True:
        count_display()

    print "Fuck you buddy!"
