import os

def transformDpToInt(line):
    words = line.split('"')
    svgWidthWithDp = words[1]
    value = 0
    lenght = len(svgWidthWithDp)
    if '.' in svgWidthWithDp:
        floatValue = float(svgWidthWithDp[0:lenght-2])
        value = int(floatValue)
    else:
        value = int(svgWidthWithDp[0:lenght-2])
    return value

arr = os.listdir()
print(arr)

for fileName in arr:
    new_lines = list()
    if fileName != 'cleaner.py' and fileName != 'bitcoin.xml':
        print(f"Opening {fileName} in read.")
        with open(fileName, "r") as fr:
            for line in fr:
                if "pt" in line:
                    line = line.replace("pt", "")
                elif "android:width=" in line:
                    width = transformDpToInt(line)
                    print(width)
                    line = f"    android:width=\"200dp\"\n"
                elif "android:height=" in line:
                    height = transformDpToInt(line)
                    newHeight = int((200*height)/width)
                    print(newHeight)
                    line = f"    android:height=\"{newHeight}dp\"\n"
                
                new_lines.append(line)

        with open(fileName, "w") as fw:
            print(f"Opening {fileName} in write.")
            fw.writelines(new_lines)