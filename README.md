# Finin

Libraries [Android Support Library][support-lib]   
[Android Architecture Components][arch]  
[Android Data Binding][data-binding]  
[Dagger 2][dagger2] for dependency injection   
[Retrofit][retrofit] for REST api communication

Gson is another popular choice and being a smaller library than Jackson, you might prefer it to avoid 65k methods limitation.
Also, if you are using

Networking, caching. There are a couple of battle-proven solutions for performing requests to backend servers, which you
should use rather than implementing your own client. We recommend basing your stack around OkHttp for efficient HTTP
requests and using Retrofit to provide a typesafe layer.

RxJava is a library for Reactive Programming, in other words, handling asynchronous events. It is a powerful paradigm, but it
also has a steep learning curve. We recommend taking some caution before using this library to architect the entire
application. We have written some blog posts on it: [1], [2], [3], [4]. For a reference app, our open source app Freesound Android makes extensive use of RxJava 2.

If you have no previous experience with Rx, start by applying it only for responses from app's backend APIs. Alternatively,
start by applying it for simple UI event handling, like click events or typing events on a search field. If you are confident
in your Rx skills and want to apply it to the whole architecture, then write documentation on all the tricky parts. Keep in
mind that another programmer unfamiliar to RxJava might have a very hard time maintaining the project. Do your best to help 
them understand your code and also Rx.

Use RxAndroid for Android threading support and RxBinding to easily create Observables from existing Android components.

A collection of samples using the Architecture Components:

Lifecycle-aware components
ViewModels
LiveData
