MessageBar
==========

An Android **Toast replacement, similar to the one seen in the GMail app**.
Multiple messages can be posted in succession and will be retained on screen rotation

![Screenshot](https://raw.github.com/MichaelFlisar/MessageBar/master/screenshot.jpg)

main customizable functionality:

* can be added to every view and adjusts the parent automatically to be capable to display a non layout relevant message bar (floating view)
* display duration
* disposing mode (automatic, button click)
* without button or with one or two buttons
* display remaining display time
* customizable message bar style
* extended messages for convenience

Hint
=====

**Study the example, it should be quite self-explanatory :-)**

Everything you need is written in the example code as comments...


Usage
=====

### Showing a message
-----------------------------------------------

A message is shown simply by calling `messageBar.show(message)` function or by the messages `message.show(messageBar)` function

### Creating an Activity/View with a message bar
-----------------------------------------------

#### Way 1:

Extend your activity from the MessageBarActivity and use the default MessageBar.
Use `activity.getMessageBar()` to use the activities message bar

#### Way 2:

Create a message bar like following, calling the constructor with the parent view/activity as container:
`MessageBar messageBar = new MessageBar(Activity container, boolean checkContainer);`
`MessageBar messageBar = new MessageBar(Activity container, boolean checkContainer, int resMessageBar);`
`MessageBar messageBar = new MessageBar(View container, boolean checkContainer);`
`MessageBar messageBar = new MessageBar(View container, boolean checkContainer, int resMessageBar);`

You can use this to use the message bar in a fragment, in a view, in an activity.... just everywhere

1. container... is the parent, in which the message bar will be shown
2. checkContainer... this will check the parent view and adjust it if necessary, meaning:
 * adding a propriete container around the view if neccessary
 * adding the message bar to the container if the container does not contain it already

#### Using a custom message bar

Keep in mind, that a custom message bar needs to contain following 4 views:

    R.id.mbContainer (ViewGroup)
        R.id.mbMessage (TextView)
        R.id.mbButton1 (TextView)
        R.id.mbButton2 (TextView)
        
Have a look into the default resources to see more. The message bar will look for the above mentioned Views and adjusts their texts and visibility 

Credits
=======

 * Roman Nurik for creating the [example][1] this library is based on.
 * [SimonVT][2] for giving me the base idea and the starting code


License
=======

    Copyright 2013 Michael Flisar

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

 [1]: https://code.google.com/p/romannurik-code/source/browse/#git%2Fmisc%2Fundobar
 [2]: https://github.com/SimonVT
