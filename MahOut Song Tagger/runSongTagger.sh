#!/bin/sh


# run SongTagger
java -jar MahOutSongTagger.jar -httpPort=6969 &

# run app in default browser
python -mwebbrowser -t http://localhost:6969/MahOutSongTagger
