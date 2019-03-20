#!/usr/bin/expect
set timeout 10
set user [lindex $argv 0]

spawn scp $user root@60.205.187.142:/forum
expect {
 "*assword:"
  {
    send "Srg971011\n"
  }
}
expect "100%"
expect eof