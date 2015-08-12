#!/bin/bash

version=$1

mkdir build_tmp/
cd build_tmp
release=PJavaCommander-$version
mkdir $release

echo "copying files"
rsync -a ../classes $release --exclude=".svn" > /dev/null
rsync -a ../lib $release --exclude=".svn" > /dev/null
cp -r ../.pjavacommander ../HOTKEYS.txt ../INSTALL.txt ../run.* $release

# make tar
echo "creating tar"
tar cvf ${release}.tar $release > /dev/null
gzip -9 ${release}.tar

echo "creating zip"
# make zip
zip -r ${release}.zip $release > /dev/null

echo "copying archives"
cp ${release}.zip ../dist
cp ${release}.tar.gz ../dist

cd ..
rm -r build_tmp

echo "$release created"
