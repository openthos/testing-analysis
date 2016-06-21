while read LINE
do
    echo $LINE
    ./test1.sh &
done < configs
wait
exit