ShortcutBadger:
===================================

The ShortcutBadger makes your Android App showing the count of unread messages as a badge on your App shortcut!

# Currently support launchers:<br/>

Sony Home Launcher<br/>
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_sony.png)

Samsung Touchwiz<br/>
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_samsung.png)

LG Launcher<br/>
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_lg.png)

HTC Launcher<br/>
![ScreenShot](https://raw.github.com/leolin310148/ShortcutBadger/master/screenshots/ss_htc.png)


Nova launcher with TeslaUnread,Apex launcher,Adw Launcher provided by <a href="https://github.com/notz"/>notz</a>



HOW TO USE
===================================
<br/>1. Download the ShortcutBadger-aar.zip and put it into your project.
<br/>2. Add the repositories path in your build.gradle.

        repositories {
            jcenter()
            maven {
                url 'file://'+project.rootDir+"/ShortcutBadger-aar"
            }
        }
<br/>3. Add dependencies for Shortcutbadger
        
        dependencies {
            compile 'me.leolin:ShortcutBadger:1.0.1@aar'
        }

<br/>4. Add the codes below:

        int badgeCount = 1;
        try {
            ShortcutBadger.setBadge(getApplicationContext(), badgeCount);
        } catch (ShortcutBadgeException e) {
            //handle the Exception
        }
<br/>5. If you want to remove the badge, just set the badgeCount as 0.
<br/>
<br/>
<br/>
<br/>
LICENSE
===================================
<br/>
        
        Copyright 2014 Leo Lin
        
        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        
            http://www.apache.org/licenses/LICENSE-2.0
        
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
<br/>       
