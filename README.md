# JavaTokenizer
JavaTokenizer is a tool based on ASTs for extracting the textual features from Java source code by transforming tokens into vectors of integers that honour the Java language rules and keywords. JavaTokenizer captures the token sequence by iterating on all fields and methods in all classes to pre-assign integer values to them. Thenceforward, it tokenizes all classes based on the tokens' pre-assignment. This approach significantly captures the data dependencies among all classes in the same project, improving the detection accuracy for code smells with larger granularity such as feature envy. An overview of JavaTokenizer is presented in the below figure.
![image](https://github.com/RanaSamy/JavaTokenizer/assets/13498033/2a44ab53-7f8a-4196-b729-d8fc1e0dcd98)
The below figures explain how JavaTokenizer tracks the classes’ dependencies by keeping a list of preassigned classes’ tokens in the program memory. JavaTokenizer is based on JavaParser to extract methods and fields in each class. Consequently, the program iterates on the list of tokens of each class to tokenize the code. A code token can be an identifier, literal, keyword, separator, or operator.

![image](https://github.com/RanaSamy/JavaTokenizer/assets/13498033/6b7f4a55-e431-4bbf-a641-541c3f6df1ba)
![image](https://github.com/RanaSamy/JavaTokenizer/assets/13498033/4d426d4c-98ac-4663-bc24-b139eb50f190)
