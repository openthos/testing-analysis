# kernelci-analysis

## download this repo
```
git clone https://github.com/caoruidong/kernelci-analysis.git

```

## install packages
```
sudo apt-get install git-core gnupg flex bison gperf build-essential 
zip curl zlib1g-dev gcc-multilib g++-multilib libc6-dev-i386 
lib32ncurses5-dev x11proto-core-dev libx11-dev lib32z-dev ccache 
libgl1-mesa-dev libxml2-utils xsltproc unzip
```

## set the configuration variables
1. edit `tmp_branch/envar`, set the correct android-x86 directory `eg: /path/to/android-x86/work`

2. run `crontab -e`, add a line.eg: `* */1 * * * /path/to/updateGIT.sh` means every one hour the updateGIT.sh is executed.

## some specific changes
pleas modify some vars in compile.sh,build.sh,like android_repo(the path to android_repo),result(path to where you want save result),ctsautotest(path to cts-autotest written by aquan),iso(path to iso after compile)
