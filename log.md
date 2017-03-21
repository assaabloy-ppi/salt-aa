170321 tue
==========

Notes on TweetNaCl performance on Android
-----------------------------------------

By Frans. Last night, I created a Github repo called assaabloy-ppi/salt-aa.
To create TweetNaCl API. Created API with unit tests and performance tests for 
three crypto_sign_x functions.

Result for crypto_sign_open1:
  My laptop, JVM 1.8: 2.0 ms.
  Android, LG-K350 (ref phone): 342 ms.

Implementation is from: https://github.com/InstantWebP2P/tweetnacl-java
On Android I modified the "debug" build config. Set "debuggable" parameters
to false. Did reduce time with about 20 ms. But no huge difference.

CONCLUSION

We still need libsodium-optimized crypto on Android.
The InstantWebP2P/tweetnacl-java implementation work as a reasonable
fallback. On server-implemementations, the pure Java version is an alternative
for production.

Overall conclusion: we need salt-aa!

