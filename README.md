# Szyfrant
Command line based program, used to encrypt and decrypt given or uploaded from file data. Uses Cesar's cipher and unicode shifting. 
Arguments:
-key (int) 
-data (string)
-mode (encryption /decryption)
-in (txt) - path to read data
-out (txt) -path to save data
-alg - alghoritm type (unicode / shift)

Example:
java Main -mode enc -key 5 -data "Przed wyruszeniem w droge nalezy zebrac druzyne"

>Uweji bdwzxejsnjr b iwtlj sfqjed ejgwfh iwzedsj

java Main -mode dec -key 5 -data "Uweji bdwzxejsnjr b iwtlj sfqjed ejgwfh iwzedsj"

Przed wyruszeniem w droge nalezy zebrac druzyne
