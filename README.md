salt-aa
=======

A Java API to TweetNaCl implementations. The following functions of the 
25 functions in [TWEET-1] are supported:

* crypto_sign_keypair.
* crypto_sign.
* crypto_sign_open.
* crypto_box_keypair.
* crypto_box.
* crypto_box_open.
* crypto_box_beforenm.
* crypto_box_afternm.
* crypto_box_open_afternm.

crypto_sign and related functions implement the ed25519 signature scheme.
crypto_box and related functions implement public key authenticated encryption 
using x25519+xsalsa20+poly1305.

Our approach is to follow the original TweetNaCl/NaCl C API closely.
This is a low-level API.

Changes from original, comments, implementation approach, etc:

* Unlike the TweetNaCl original, this library does not depend on random 
  data. All functions are pure functions; they are deterministic.
  
* C functions that always return 0 are implemented in Java using 
  Java methods with the "void" return type.
  
* Exceptions are sometimes used instead of returning an error code.

* Byte arrays are used with their lengths. That is, we do not pass in 
  offsets and size to allow for C-like array operations.
  
* Memory allocation is normally done by the caller.


References
==========

* [TWEET-1]. Daniel J. Bernstein, Bernard van Gastel, Wesley Janssen, Tanja Lange, 
  Peter Schwabe, Sjaak Smetsers. 
  *TweetNaCl: a crypto library in 100 tweets*. LatinCrypt 2014, to appear.
  
* [TWEET-2]. Website: http://tweetnacl.cr.yp.to.

