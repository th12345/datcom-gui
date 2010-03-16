                            DD2JSB.jar  README

This program takes Datcom output files and produces a set of tables which may be used in a JSBim XML file.

Usage.

1. Run Datcom.
2. Take the output file (For006.dat). You may rename this file to something more useful - e.g. F16WingBodyTailRoll.out.
3. Run DD2JSB.jar.
4. On the first screen browse and open your For006.dat file.
5. Optionally edit the Path and Case fields. Default output if these goes to C:\Datcom\Aircraft\.
6. select "Import"
5. On the next screen select "Build Tables"
6. When "Finished Processing" is reported you should have a set of data tables in your chosen output directory.

 
There will be a separate directory created for each input file-name.

Below this there will be a set of subdirectories, one for each Datcom "CASEID" statement.

Each of these subdirectories will contain a set of aerodynamic derivative tables in a form suitable for cut and paste into a JSBSim input XML file.

It is suggested that Aeromatic (http://jsbsim.sourceforge.net/aeromatic.html)is used to provide a suitable template file. 

For many derivatives Datcom only outputs values for the first Alpha value. In this case DD2JSB produces two output tables. The first is the direct output, which has a lot of empty cells. The second assumes that the derivative is constant for all Alpha values, and thus is printed for variation in Mach and Altitude only. In the case of Clq these tables are Clqtab.txt and Clqtab1.txt.

The user is advised to look carefully at the values in these tables. If they are constant (or nearly so) for all Altitudes (or Mach or Alpha) then he should edit out the Altitude (or Mach or Alpha) table entries before using them in JSBSim. Alternatively re-run Datcom with NALT (or NMACH or NALPHA) set to 1.0 .

Also look for replacing a table by a polynomial approximation formula such as:-
                       y = a + bx + cx2

Any means possible of reducing the number of dimensions in each table will speed up JSBSim operation. 

Some table entries will be set to NaN, NDM, NA, ******* or blank. These are because Datcom was unable to calculate a valid value for one reason or another. The user should read the Datcom user manual for advice. In some cases it will be necessary to estimate the value by other means.     

For those who want to play with the code the decoding is based upon the fact that Fortran output is defined by FORMAT statements which result in data tables with fixed width data columns. Datcom often produces tables with spaces, “NAN” or “NDM” where a number would normally be expected, but the column width remains constant.

The attached examples are for Bill Galbraith's Citation and the BAC TSR2. The latter is an aircraft which featured in my early career. This TSR2 model is very much a work in progress, so do not read too much into the numbers. 


Please report any bugs or suggestions to alan.teeder65”at”imperial.ac.uk

As this my first ever Java project, try to make comments helpful.

Many thanks to Aleksey Matyushev, who's DD2.jar program was used as a starting point for this exercise. Some of his code is still here!

Alan Teeder









