# CleanArchAndroid
An example of clean architecture on Android (with and without Dagger)


Without Dagger
----

At this point, I'm not even sure if this is a good architecture anymore.
For two views/screens, there are a LOT of classes.

One definite problem is that the QuestionsPresenterImpl seems to be the all encapsulating Root. Ideally, there should be something else outside it that should be managing both all Presenters. If that happens then both Presenters would be independent of each other.

I have to do this weird dance to get the checked choice back to the VotePresenterImpl, not sure how to avoid that, even with the CheckBoxGroup class.


Good things
---
- Interactors only hold weak references to the Presenters, and those references are cleared after they're used for that "event".
- Presenters only hold reference to their own child views and only interact with those views, not anything inside them.
- Views interact directly with their own children, the "actual" views.
- Network layer looks like its completely separate from everything, but that's the case when using Retrofit anyway.
- Activity is basically just chilling since everything is being done in the child views, which are presenters.

Bad things
----
- Interactors seem to be very boilerplate-heavy.

Right now, this app doesn't have much data handling. Just passing live data from the network down to the interactor -> presenter -> view. I wonder how big it would become if there is a data layer, maybe it'll be an abstraction between the network and interactor.

I really don't like how the interactor seems to have all the callback stuff related to Retrofit in it, maybe there's a better way to do that.


Testability
-----
Not sure really. Will have to revisit after writing the Dagger version.


Next steps
-----
Now that I know which modules and components are needed, I want to write this with Dagger. See how much time and code I can save. It seems the most code is required to manage views and present them.