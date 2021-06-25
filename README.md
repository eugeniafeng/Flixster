# Project 2 - Flixster

Flixster shows the latest movies currently playing in theaters. The app utilizes the Movie Database API to display images and basic information about these movies to the user.

Time spent: **9** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **scroll through current movies** from the Movie Database API
* [x] Display a nice default [placeholder graphic](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#advanced-usage) for each image during loading
* [x] For each movie displayed, user can see the following details:
  * [x] Title, Poster Image, Overview (Portrait mode)
  * [x] Title, Backdrop Image, Overview (Landscape mode)
* [x] Allow user to view details of the movie including ratings within a separate activity 

The following **stretch** features are implemented:

* [x] Improved the user interface by experimenting with styling and coloring.
* [x] Apply rounded corners for the poster or background images using [Glide transformations](https://guides.codepath.org/android/Displaying-Images-with-the-Glide-Library#transformations)
* [x] Apply the popular [View Binding annotation library](http://guides.codepath.org/android/Reducing-View-Boilerplate-with-ViewBinding) to reduce boilerplate code.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the details screen. (Added the feature that videos always appear in landscape mode to be full-screen.)

The following **additional** features are implemented:

* [x] Allow user to view vote count and release date within separate activity and position overview under the lower of the release date or the image.
* [x] Implement the same tap-to-play YouTube functionality from the landscape view of the movies list.
* [x] Display a “play” overlay image over the backdrop on the details activity as a visual cue to the user.
* [x] Filter out any videos that aren’t YouTube, as these would not work with the player view.
* [x] Add a custom launcher icon.
* [x] Allow the user to scroll on the movie details page in both orientations.
* [x] Allow video trailers to be played in full-screen using the YouTubePlayerView from the MainActivity screen.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/eugeniafeng/Flixster/blob/master/walkthrough.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Here's a walkthrough of the placeholder images:

<img src='https://github.com/eugeniafeng/Flixster/blob/master/walkthrough2.gif' title='Placeholder Walkthrough' width='' alt='Placeholder Walkthrough' />

GIF created with [LiceCap](https://www.cockos.com/licecap/).

## Notes

I had an issue with the YouTube player giving an error upon initialization, but this was fixed by adding a few lines in the manifest file to allow the YouTube service to start. I also had some trouble applying View Binding in MovieAdapter since I had originally created the binding using a global variable, but we later found the bug and switched it to a local variable that would create a new binding for each cell.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright 2021 Eugenia Feng

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
