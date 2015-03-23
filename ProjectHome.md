Website: http://openAE.org

The United States Air Force (USAF) Stability and Control Digital DATCOM is a multi-
purpose program written in FORTRAN that analyzes the stability and control of a given
aircraft. Through basic geometric properties of the aircraft, along with the given flight
conditions and propulsion elements, the DATCOM is able to compile coefficients which
closely describe the performance and stability of the aircraft at the given conditions.

DATCOM was written in the 1970’s and due to the rapid development of technology
since then, some aspects of the program are outdated. One of the key outdated features is
the user interface. The input file (for005.dat) for DATCOM is written in a text document
format for which the standard rules of FORTRAN apply. With these restrictions, it is
time consuming to compile and troubleshoot the input file.

Another outdated feature is the use of the DOS command prompt to run the program. The
DOS command prompt, although still an integral part of computing today, was replaced
by more user friendly Operating Systems (OS) which are centered around easy to use
Graphical User Interfaces (GUI) such as Windows. With the transition to more GUI
intensive OS’s, the knowledge of DOS specific commands and their uses has become an
archaic art.

DATCOM Release 2 (DR2) has been specifically developed to combat these two
outdated features. DR2 is written completely in Sun Microsystems Java SE and uses the
Java Virtual Machine (JVM) to interface with USAF DATCOM. The interface is
completely coded into DR2; as a result, no modification to the original DATCOM code is
needed.

DR2 uses basic GUI’s to allow the user to easily compile the input file, import an
existing input file, and run DATCOM without any knowledge of DOS or the necessary
FORTRAN formatting needed for the input file.
Another feature that was added to DR2, which the original DATCOM did not directly
possess, is the compiling and export of stability and performance coefficients. This
feature was added with the specific intention of compiling three dimensional stability
tables which can be copied straight into the FlightGear (using the JSBsim flight model)
open source flight simulator. This allows for an aircraft to be analyzed in DATCOM and
its flight to be tested in FlightGear.

PLEASE NOTE: This document assumes that the user has had some basic exposure to
the DATCOM program. For further reference on DATCOM please consult the Manual
Volumes that are bundled with DATCOM. Also, this program is in BETA development
stage and might have some bugs, as a result, please look through this manually carefully
before using DR2 and it's features.