package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder;
/*
 * %  traceroute fr.wikipedia.org
traceroute to rr.knams.wikimedia.org (145.97.39.155), 30 hops max, 38 byte packets
 1  80.67.162.30 (80.67.162.30)  0.341 ms  0.300 ms  0.299 ms
 2  telehouse2-gw.netaktiv.com (80.67.170.1)  5.686 ms  1.656 ms  0.428 ms
 3  giga.gitoyen.net (80.67.168.16)  1.169 ms  0.704 ms  0.563 ms
 4  62.4.73.27 (62.4.73.27)  2.382 ms  1.623 ms  1.297 ms
 5  ge5-2.mpr2.cdg2.fr.above.net (64.125.23.86)  1.196 ms ge9-4.mpr2.cdg2.fr.above.net (64.125.23.102)  1.290 ms ge5-1.mpr2.cdg2.fr.above.net (64.125.23.82)  30.297 ms
 6  so-5-0-0.cr1.lhr3.uk.above.net (64.125.23.13)  41.900 ms  9.658 ms  9.118 ms
 7  so-7-0-0.mpr1.ams5.nl.above.net (64.125.27.178)  23.403 ms  23.209 ms  23.703 ms
 8  64.125.27.221.available.above.net (64.125.27.221)  19.149 ms so-0-0-0.mpr3.ams1.nl.above.net (64.125.27.181)  19.378 ms 64.125.27.221.available.above.net (64.125.27.221)  20.017 ms
 9  PNI.Surfnet.ams1.above.net (82.98.247.2)  16.834 ms  16.384 ms  16.129 ms
10  af-500.xsr01.amsterdam1a.surf.net (145.145.80.9)  21.525 ms 20.645 ms  24.101 ms
11  kncsw001-router.customer.surf.net (145.145.18.158)  20.233 ms 16.868 ms  19.568 ms
12  gi0-24.csw2-knams.wikimedia.org (145.97.32.29)  23.614 ms  23.270 ms  23.574 ms
13  rr.knams.wikimedia.org (145.97.39.155)  23.992 ms  23.050 ms 23.657 ms

*
*
**/

import java.util.ArrayList;

import model.GetTreeIP;

public class AppController {

	static public void main(String[] args) throws IOException

	{
	
		new GetTreeIP(); 
	}

}