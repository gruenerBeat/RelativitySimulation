#! /usr/bin/bash
. ../project.cfg
mkdir -p ../bin
rm -rf ../bin/$project_version
mkdir ../bin/$project_version
rm -rf ../build
mkdir ../build
cp -r ../source/* ../build/
find ../build -type f -iregex '.*\.cpp' -print0 |
while IFS= read -r -d '' src; do
    obj="${src%.cpp}.o"
    g++ -c $src -o $obj
done
rm -f $(find ../build -type f -iregex ".*\.cpp")
cp -r ../build/modules/ ../bin/$project_version/ 
cp -r ../build/resource/ ../bin/$project_version/ 
g++ $(find ../build -type f -iregex ".*\.o")  -w -lmpfr -lgmpxx -lgmp -lSDL2 -o ../bin/$project_version/$project_name
echo "./$project_name" > ../bin/$project_version/run.sh
chmod a+x ../bin/$project_version/run.sh
