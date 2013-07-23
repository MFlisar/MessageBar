INFO
=========

First release will come tomorrow evening... 24.07.2013

MessageBar
==========

An Android Toast replacement, similar to the one seen in the GMail app.
Multiple messages can be posted in succession and will be retained on screen rotation

Customizable values:
* showing duration
* disposing mode(automatic, button click)
* without button or with one or two buttons


Usage
=====

Way 1:
------

Extend your activity from the MessageBarActivity

Way 2:
------


Way 3:
------


Showing a message
-----------------

A message is shown simple by calling Activity's `getMessageBar().show(Message)` function or by the messages `message.show(messageBar)` fucntion


Credits
=======

 * Roman Nurik for creating the [example][1] this library is based on.
 * SimonVT for giving me the base idea and the starting code


License
=======

    Copyright 2012 Michael Flisar

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
