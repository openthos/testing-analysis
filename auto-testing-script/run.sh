#!/bin/bash
./autoTest.sh v install localhost ../android-x86.raw "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
./autoTest.sh r install 192.168.2.82 /dev/sda40 "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
./autoTest.sh r install 192.168.2.80 /dev/sda40 "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
./autoTest.sh r install 192.168.2.17 /dev/sda40 "-p android.acceleration --disable-reboot" ../xyl_android_x86_64_6.0.iso
