# MaterialPreferences 

This library is a set of VIEWS (not Preferences) and is aimed to solve this problems:
* Preferences look ugly on pre-lollipop.
* Preferences are not flexible. For example, it can be problematic to embed them to another screen (especially for fragment-haters).
* Preferences don't allow you to show custom selection dialogs.

![Screenshots](https://raw.githubusercontent.com/yarolegovich/materialpreferences/master/art/screenshots.png)

## Gradle 
Add this into your dependencies block:
```
compile 'com.yarolegovich:mp:1.0.5' 
```
For [LovelyInput](#lovelyinput) you will also need this:
```
compile 'com.yarolegovich:lovelyinput:1.0.2'
```

## Wiki 
Sample project that shows how to work with different library features is available as [sample module](https://github.com/yarolegovich/MaterialPreferences/tree/master/sample).
You can also [download APK](https://github.com/yarolegovich/MaterialPreferences/raw/master/sample/sample.apk) and play with it. 

### Contents

1. [Types of preferences](#types-of-preferences)
2. [UserInputModule](#userinputmodule)  
  Allows you to show any dialogs (or event not dialogs) you want, when some preferencesare clicked.
3. [StorageModule](#storagemodule)  
  Allows you to save/retrieve values to/from any data source.
4. [LovelyInput](#lovelyinput)  
  Implementation of UserInputModule based on [LovelyDialog](https://github.com/yarolegovich/LovelyDialog) library.
 
### Types of preferences
There are 2 ViewGroup classes: 
* [MaterialPreferenceScreen](#materialpreferencescreen)
* [MaterialPreferenceCategory](#materialpreferencecategory)

And a lot of AbsMaterialPreference subclasses:
* [MaterialStandardPreference](#materialstandardpreference)
* [MaterialRightIconPreference](#materialrighticonpreference)
* [AbsMaterialTextValuePreference](#absmaterialtextvaluepreference)
 * [AbsMaterialListPreference](#absmateriallistpreference)
    * [MaterialChoicePreference](#materialchoicepreference)
    * [MaterialMultiChoicePreference](#materialmultichoicepreference)
 * [MaterialEditTextPreference](#materialedittextpreference)
* [AbsMaterialCheckablePreference](#absmaterialcheckablepreference)
 * [MaterialSwitchPreference](#materialswitchpreference)
 * [MaterialCheckboxPreference](#materialcheckboxpreference)
* [MaterialColorPreference](#materialcolorpreference)
* [MaterialSeekBarPreference](#materialseekbarpreference) 
 
Each preference contains optional title, summary and icon (icon can be tinted). Also for some preferences
it is mandatory to set default value and key (at least when dealing with SharedPreference as [StorageModule](#storagemodule)).
```xml
<com.yarolegovich.mp.AnyAbsMaterialPreferenceSubclass
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:mp_icon="@drawable/your_icon"
    app:mp_icon_tint="@color/textSecondary"
    app:mp_title="@string/your_title"
    app:mp_summary="@string/your_summary"
    app:mp_key="@string/pkey_your_key"
    app:mp_default_value="42|true|any string"/>
```

#### MaterialPreferenceScreen
ViewGroup.In essence - scrollable LinearLayout. Use it or not is a matter of preference, but it has some useful features.
```java
public void setVisibilityController(int controllerId, List<Integer> controlledIds, boolean showWhenChecked);
public void setVisibilityController(final AbsMaterialCheckablePreference controller, final List<Integer> controlledIds, final boolean showWhenChecked);
```
Sets some checkable preference to control visibility of views with specified ids. Last parameter is used to define when views should be visible (if preference is checked or not).
```java
MaterialPreferenceScreen screen = (MaterialPreferenceScreen) findViewById(R.id.preference_screen);
screen.setVisibilityController(R.id.pref_auto_loc, Arrays.asList(R.id.pref_location), false);
```
Now when our R.id.pref_auto_loc preference is checked/unchecked - R.id.pref_location will be hidden/shown. 

#### MaterialPreferenceCategory
ViewGroup. Has optional attributes
```xml
app:mpc_title="@string/your_title"
app:mpc_title_color="@color/your_color"
```
to specify a title and title color for category. In essence - CardView with vertical LinearLayout inside it. 

#### MaterialStandardPreference
The simplest preference. Doesn't perform anything (until you set OnClickListener on it). Saves nothing.

#### MaterialRightIconPreference
The same as MaterialStandardPreference, but icon is on the right. Saves nothing.

#### AbsMaterialTextValuePreference
Sometimes I want to show current value of preference in summary or maybe on the right of the row. With sublcasses of 
MaterialTextInputPreference you have an option to do this by using: 
```xml
<!-- default is notShow --> 
app:mp_show_value="notShow|onBottom|onRight" 
```
attribute. It can take one of the listed values, that I think are pretty self-explanatory. Any subclass has access to this functionality.

#### AbsMaterialListPreference
Subclass of [AbsMaterialTextValuePreference](#absmaterialtextvaluepreference). Defines two attributes:
```xml
app:mp_entry_descriptions="@array/some_string_array"
app:mp_entry_values="@array/some_string_array"
```
The first one contains values that will be shown in the dialog when preference is clicked. Second contains values that will be saved
after some item is selected. If one attribute is omitted - another one will be used for both purposes.

#### MaterialChoicePreference
Subclass of [AbsMaterialListPreference](#absmateriallistpreference). On click shows dialog with items from mp_entry_descriptions,
when any item is selected - item with corresponding index from mp_entry_values will be saved. Saves String value.

#### MaterialMultiChoicePreference
Subclass of [AbsMaterialListPreference](#absmateriallistpreference). On click shows dialog with items from mp_entry_descriptions, 
any number of items can be checked and after the dialog is dismissed - items from mp_entry_values with indices that correspond to 
indices of checked items will be saved. Saves Set<String> value.
Class also defines attribute:
```xml 
app:mp_default_selected="@array/checked_items"
```
To specify items selected by default.

#### MaterialEditTextPreference
Subclass of [AbsMaterialTextValuePreference](#absmaterialtextvaluepreference). On click shows dialog with EditText and confirm button. 
After confirm button is clicked - text will be saved. Saves String value.

#### AbsMaterialCheckablePreference
Contains some checkable view and changes its state on click. All subclasses save boolean.

#### MaterialSwitchPreference
Subclass of [AbsMaterialCheckablePreference](#absmaterialcheckablepreference). Contains SwitchCompat view on the right.

#### MaterialCheckboxPreference
Subclass of [AbsMaterialCheckablePreference](#absmaterialtextvaluepreference). Contains CheckBox view on the right. 

#### MaterialColorPreference
Contains custom ColorView on the right that reflects currently selected color. Defines attributes:
```xml 
app:mp_initial_color="@color/your_color"
app:mp_border_color="@color/your_color"
app:mp_border_width="@dimen/your_width"
app:mp_indicator_shape="circle|square"
```
On click shows color picker dialog. By default it uses [VintageChroma](https://github.com/MrBIMC/VintageChroma). Saves color int.

#### MaterialSeekBarPreference
Contains AppCompatSeekBar view under the summary and optional TextView that reflects current value. Defines attributes:

```xml 
app:mp_max_val="12"
app:mp_min_val="1"
app:mp_show_val="true"
```
TextView with current value will be shown if mp_show_val is set to true (which is not by default). Saves int.

### UserInputModule

Interface that defines methods called when some preferences are clicked:
* MaterialEditTextPreference -> showEditTextInput(...)
* MaterialChoicePreference -> showSingleChoiceInput(...)
* MaterialMutltiChoicePreference -> showMultiChoiceInput(...)
* MaterialColorPreference -> showColorSelectionInput(...)

If you want some special look of your dialogs you need to implement this interface together with UserInputModule.Factory.
Then you need to set it as default UserInputModule or as UserInputModule for specific screen.

To see real example refer to [this project](https://github.com/yarolegovich/MaterialPreferences/tree/master/lovelyinput).

Suppose you have classes:
```java
public class MyFancyInputModule implements UserInputModule { ... }

public class MyFancyInputModuleFactory implements UserInputModule.Factory { ... }
```
Then you need to do the following to set it as a default module:
```java 
MaterialPreferences.instance().setUserInputModule(new MyFancyInputModuleFactory());
```
Or set is as module for some specific screen:
```java
MaterialPreferencScreen screen = (MaterialPreferenceScreen) findViewById(R.id.preference_screen);
screen.setUserInputModule(new MyFancyInputModuleFactory());
```

### StorageModule

Interface that defines methods called by preferences to save/retrieve value. This gives you additional flexibility, because preferences
now can be used for different purposes - you can even [initialize POJOs](https://github.com/yarolegovich/MaterialPreferences/blob/master/sample/src/main/java/com/yarolegovich/materialprefsample/FormInitializer.java) with the help of this feature.

To set your storage module follow the same procedure as with UserInputModule.

Suppose you have classes:
```java
public class MyCoolStorageModule implements StorageModule { ... }

public class MyCoolStorageModuleFactory implements StorageModule.Factory { ... }
```
Then you need to do the following to set it as a default module:
```java 
MaterialPreferences.instance().setStorageModule(new MyCoolStorageModuleFactory());
```
Or set is as module for some specific screen:
```java
MaterialPreferencScreen screen = (MaterialPreferenceScreen) findViewById(R.id.preference_screen);
screen.setStorageModule(new MyCoolStorageModuleFactory());
```
If you want to store data inside preferences, but not default - you can use:
```java
new SharedPrefsStorageFactory("filename")
```

### LovelyInput
Implementation of [UserInputModule](#userinputmodule) combined with my another library - [LovelyDialog](https://github.com/yarolegovich/LovelyDialog). 
For information on how to use it - [check sample](https://github.com/yarolegovich/MaterialPreferences/blob/master/sample/src/main/java/com/yarolegovich/materialprefsample/MainActivity.java#L63).
You can use not only 
```java
setKeyIconMappings(Set<String, Integer> mappings);
```
but also 
```java
setKeyTitleMappings(Set<String, String> mappings);
setKeyMessageMappings(Set<String, String> mappings);
```
[Source code](https://github.com/yarolegovich/MaterialPreferences/tree/master/lovelyinput/src/main/java/com/yarolegovich/lovelyuserinput) of module can serve you as an example of custom UserInputModule. 

### License
```
Copyright 2016 Yaroslav Shevchuk

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
