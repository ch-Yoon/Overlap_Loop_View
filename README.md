# Hello, welcome to OverlapLoopView    

If you drag the view in the direction you want, you can go to the next page. You can show the full picture on the screen and give it the effect of crossing the page as if you were passing the paper. The logic for calculating the area, drag speed, and drag direction was applied. So, even if you do not drag a long, you can go to the next page.

## Demo

![ezgif-4-ff6f7eef9911](https://user-images.githubusercontent.com/20294749/53284412-80159c00-3797-11e9-8469-25a574f331d2.gif)

## Structure

- The top view is the interaction with user, other views are the preload
![structure](https://user-images.githubusercontent.com/20294749/53284785-50b55e00-379c-11e9-9c0a-f030fd2f2caa.png)

- Views are not created continuously. Views are recycled.
![ezgif-4-66691bf3e8ed](https://user-images.githubusercontent.com/20294749/53284980-7c394800-379e-11e9-88b4-1cd02db10422.gif)



## Setup

- **step 1 : Add it in your root build.gradle at the end of repositories**
~~~gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
~~~

- **step 2 : Add the dependency**
~~~gradle
	dependencies {
	        implementation 'com.github.ch-Yoon:Overlap_Loop_View:0.0.2'
	}
~~~

## How to use?
- I wrote a very simple example code -> [**Sample Code**](https://github.com/ch-Yoon/Overlap_Loop_View/tree/master/app/src/main/java/com/view/loop/overlap/why/yoon/ch/overlaploopview)
- sample demo    

![ezgif-4-c504aaa1cc81](https://user-images.githubusercontent.com/20294749/53284618-25ca0a80-379a-11e9-9d06-3878090deab8.gif)



## License
~~~
Copyright 2019 ch-Yoon

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
~~~
