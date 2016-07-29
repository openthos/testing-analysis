#!/usr/bin/python
import os
import sys
in_file=open("/boot/grub/grub.cfg",'r')
out_file=open("grub.cfg2",'w')

#/dev/sdaN

partnumb=(sys.argv[1])[8:]
print partnumb


#grep sda+partnumb  /boot/grub/grub.cfg

pattn="grep 'Android-x86 sda"+partnumb+"' /boot/grub/grub.cfg"
print pattn

if(os.system(pattn)==0):
    sys.exit()





cc="""menuentry 'Android-x86 sdaPARTNUMB' {
	set root='hd0,gptPARTNUMB'
	linux /android-2016-02-29/kernel quiet root=/dev/ram0 androidboot.hardware=android_x86_64 SRC=/android-2016-02-29
	initrd /android-2016-02-29/initrd.img
}
"""
print cc


cc=cc.replace('PARTNUMB',partnumb)
print cc


while True:
    line=in_file.readline()
    if not line : break
    if line.startswith("menuentry 'Ubuntu'"):
        out_file.write(cc)
    out_file.write(line)
in_file.close()
out_file.close()
os.system("sleep 3")
os.system("cat ./grub.cfg2 > /boot/grub/grub.cfg")
os.system("sleep 3")

