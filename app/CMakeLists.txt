cmake_minimum_required(VERSION 3.4.1)

add_library(
             bspatch
             SHARED
             src/main/jni/bspatch.c )

find_library( log-lib
              log )

target_link_libraries(
                       bspatch
                       ${log-lib} )