# Parser

A simple parser that evaluates normalized Redux condition structure and runs it
against provided entity. 

## Usage

Run ``(-main starting-selection entity)`` where both parameters are optional.
If none supplied test values will be evaluated.

starting-selection structure:
 ```
 {
   matchType: 'all',
   groups: {
     byId: {
       initial_group: {
         id: 'initial_group',
         /**
         * real groups split in terms of matchtype
         * so every node will be resulted to all until implemented
         */
         matchType: 'all',
         filterGroups: [
           'default_filter_group'
         ]
       }
     },
     allIds: ['initial_group']
   },
   filterGroups: {
     byId: {
       default_filter_group: {
         id: 'default_filter_group',
         matchType: 'all',
         filters: ['initial_filter']
       }
     },
     allIds: ['default_filter_group']
   },
   filters: {
     byId: {
       initial_filter: {
         id: 'initial_filter',
         field: '',
         fieldValue: '',
         conditionAction: '',
         conditionValue: ''
       }
     },
     allIds: ['initial_filter']
   }
 }
 ```

Entity:
```
{
    "name": "Test"
}
```

## License

MIT License

Copyright (c) 2020 Mark Parfenov

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.