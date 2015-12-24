# CleanArchAndroid
An example of clean architecture on Android (with and without Dagger)

With Dagger (see history of this file to see an old evaluation of an architecture without Dagger)
----
Seems pretty good if I do say so myself. Largely based on https://github.com/grandstaish/hello-mvp-dagger-2/

Opted to use the Compartment library to bind "view" (which is not the Android View) to the Presenter, instead of Mosby. Mainly because Compartment seemed more lightweight and straight-forward.

Also using Icepick to save basic view state information for configuration changes.

Model
----
Model is a Singleton in this app, can use any mix of data APIs. Right now, it goes wherever the data is found (local database, then network). 

There are a few of ways to hit the network to keep the data up-to-date:
- A basic time-since-refresh variable that triggers 
- Compare timestamp since last data update with the network

Not a point of worry anymore. DBFlow seems to be okay for local database as well.

View
----
Using the custom "View" interface, it becomes very easy to create a presenter and specifying the requirements of the view that will be controlled with it. Still have to investigate how to manage/cleanup the PublishSubjects, especially in VoteFragment. State information is retained using the Icepick library.

Presenter
----
Simple job, load the data from the model and let the view know about the data. Also, thanks to Compartment, it persists through configuration changes.


Good things
----
- Dagger allows for a lot of perspective in regards to the lifecycle of components. Like AppModel (data layer) should be a singleton, which also deals with the network and database layers by itself. Can be tested easily under different network/database scenarios.

- Presenters don't need to worry about anything, data layer is provided using Dagger and the View layer is called in an observable for updates. Can be tested easily by mocking the data layer and making sure the correct View methods are called in different scenarios.

- Views don't have to worry about app logic or data layer. Can probably be easily tested by just calling the PublishSubjects under different scenarios and seeing if the behavior is correct.

- Rotation and other configuration changes are super easy to manage, especially with Icepick to retain information about the state of the view to apply later with the PublishSubjects and reloading the data using the Presenter. Perhaps a ViewState object could be declared if the retained information is complex enough. Right now, data is loaded from the Presenter (which could fetch it from memory or db) and then view state changes are applied.

Bad things
----
- View layers are code-heavy, and they're gonna get heavier as animations are introduced to the fold.

Next steps
-----
- Write tests and see how testable this is
- See if an animation layer can be abstracted away from the View
- See how to clean up the View layer.
- See how to observe changes in Network layer from AppModel and save them locally. Right now, the Network layer is responsible for calling the Database layer, which shouldn't be the case.