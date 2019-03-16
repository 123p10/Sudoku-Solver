import java.util.ArrayList;
import java.util.Arrays;

/*
 * Hidden Triples
 * 	-Three doubles
 * -One triple two doubles
 * 
 * Naked Triples
 * 	-Three doubles
 * 
 * Hidden Doubles 
 * 	-All of it
 * */

public class Container {
	Cell cells[];
	int i = 0;
	int hiddenS[] = {0,0,0,0,0,0,0,0,0,0};
	ArrayList<Pair> nakedD;
	ArrayList<Triple> nakedT;
	public Container() {
		cells = new Cell[9];
		nakedD = new ArrayList<Pair>();
		nakedT = new ArrayList<Triple>();
	}
	public void addCell(Cell cell) {
		cells[i] = cell;
		i++;
	}
	public void checkCells() {
		generalLoop();
	}
	public int checkSolution() {
		ArrayList<Integer> vals = new ArrayList<Integer>();
		for(int i = 0;i < 9;i++) {
			if(vals.contains(cells[i].getValue())) {return 0;}
			vals.add(cells[i].getValue());
		}
		return 1;
	}
	public void generalLoop() {
		for(int a = 0;a < 9;a++) {
			if(cells[a].getValue() != -1) {
				nakedSingle(a);
			}
			else {
				cells[a].checkValue();

				hiddenSingle(a);
				addNakedDouble(a);
				addNakedTriple(a);
				
			}
			
		}
		//Gonna be honest don't know why this is needed but it works
		if(Board.lastChangedCounter > 2) {
			specialNakedTriple();
			searchHiddenTriple();

			checkHiddenSingle();
			
			Arrays.fill(hiddenS, 0);
	
			for(int a = 0;a < 9;a++) {
				hiddenSingle(a);
			}
		}

		nakedD.clear();
		nakedT.clear();
		Arrays.fill(hiddenS, 0);

	}
	public void nakedSingle(int a) {
		for(int b = 0;b < 9;b++) {
			if(a != b && cells[b].getValue() == -1) {
				cells[b].removePossibleValue(cells[a].getValue());
				cells[b].checkValue();

			}
		}
	}
	public void hiddenSingle(int a) {
		for(int i = 1;i <= 9;i++) {
			if(cells[a].hasPossibleValue(i) == 1) {
				hiddenS[i]++;
			}
		}
	}
	public void checkHiddenSingle() {
		for(int i = 1;i <= 9;i++) {
			if(hiddenS[i] == 1) {
				for(int b = 0;b < 9;b++) {
					if(cells[b].getValue() == -1) {
						if(cells[b].hasPossibleValue(i) == 1) {
							cells[b].setValue(i);
							//System.out.println("Hidden Single: " + i);
						}
					}
				}
			}
		}
	}
	public void addNakedDouble(int a) {
		if(cells[a].possibleValues.size() == 2) {
			boolean exists = false;
			for(int i = 0;i < nakedD.size();i++) {
				if(nakedD.get(i).checkEquality(cells[a].possibleValues.get(0), cells[a].possibleValues.get(1)) == 1) {
					exists = true;
					for(int b = 0;b < 9;b++) {
						if(cells[b].possibleValues.size() == 2) {
							if(nakedD.get(i).checkEquality(cells[b].possibleValues.get(0),cells[b].possibleValues.get(1)) == 0) {
								cells[b].removePossibleValue(nakedD.get(i).getFirst());
								cells[b].removePossibleValue(nakedD.get(i).getSecond());
							}
						}
						if(cells[b].possibleValues.size() > 2) {
							cells[b].removePossibleValue(nakedD.get(i).getFirst());
							cells[b].removePossibleValue(nakedD.get(i).getSecond());
						}
						
					}
					nakedD.remove(i);
					break;
				}
			}
			if(exists == false) {
				nakedD.add(new Pair(cells[a].possibleValues.get(0), cells[a].possibleValues.get(1)));
			}
		}
	}
	public void addNakedTriple(int a) {
		if(cells[a].possibleValues.size() == 3) {
			boolean exists = false;	
			for(int i = 0;i < nakedT.size();i++) {
				if(nakedT.get(i).checkEquality(cells[a].possibleValues.get(0), cells[a].possibleValues.get(1), cells[a].possibleValues.get(2)) == 1) {
					exists = true;
					nakedT.get(i).increaseCount();
					if(nakedT.get(i).getCount() == 3) {
						for(int b = 0;b < 9;b++) {
							if(cells[b].possibleValues.size() == 3) {
								if(nakedT.get(i).checkEquality(cells[b].possibleValues.get(0), cells[b].possibleValues.get(1), cells[b].possibleValues.get(2)) == 0) {
									cells[b].removePossibleValue(nakedT.get(i).getFirst());
									cells[b].removePossibleValue(nakedT.get(i).getSecond());
									cells[b].removePossibleValue(nakedT.get(i).getThird());
								//	System.out.println("Naked Triple");
								}
							}
							if(cells[b].possibleValues.size() != 3) {
								cells[b].removePossibleValue(nakedT.get(i).getFirst());
								cells[b].removePossibleValue(nakedT.get(i).getSecond());
								cells[b].removePossibleValue(nakedT.get(i).getThird());
							//	System.out.println("Naked Triple");
							}
						}
						nakedT.remove(i);
						break;
					}
				}
			}
			if(exists == false) {
				nakedT.add(new Triple(cells[a].possibleValues.get(0),cells[a].possibleValues.get(1),cells[a].possibleValues.get(2)));
			}
		}
	}
	public void searchHiddenTriple() {
		ArrayList<Integer> tS = new ArrayList<Integer>();
		ArrayList<Integer> dS = new ArrayList<Integer>();

		for(int i = 1;i <= 9;i++) {
			if(hiddenS[i] == 3) {tS.add(i);}
			if(hiddenS[i] == 2) {dS.add(i);}
		}
		if(tS.size() < 2) {return;}
		if(tS.size() == 2 && dS.size() < 1) {return;}
		//Analyze all threes
		//eg. 129 129 129
		for(int i = 0;i < tS.size();i++) {
			for(int j = 0;j < tS.size();j++) {
				if(i != j) {
					for(int k = 0;k < tS.size();k++) {
						if(i != k && j != k){
							int count = 0;
							HiddenTriple hT = new HiddenTriple(tS.get(i),tS.get(j),tS.get(k));
							for(int b = 0;b < 9;b++) {
								if(cells[b].getValue() == -1 && hT.numContains(cells[b].possibleValues) >= 2) {
									count++;
								}
								if(hT.numContains(cells[b].possibleValues) == 1) {
									count = -10;
								}
							}
							if(count == 3) {
							//clearTriple(hT);
								//System.out.println("Found triple" + hT.getNth(0) + " " + hT.getNth(1) + " " +hT.getNth(2));
								return;
							}
						}
					}
				}
			}
		}
		//two threes one double
		// 129 129 12
		//the nine is the double
		if(dS.size() > 0) {
			for(int i = 0;i < tS.size();i++) {
				for(int j = 0;j < tS.size();j++) {
					for(int k = 0;k < dS.size();k++) {
						if(i != j) {
							int count = 0;
							HiddenTriple hT = new HiddenTriple(tS.get(i),tS.get(j),dS.get(k));
							for(int b = 0;b < 9;b++) {
								if(cells[b].getValue() == -1 && hT.numContains(cells[b].possibleValues) >= 2) {
									count++;
								}
								if(hT.numContains(cells[b].possibleValues) == 1) {
									count = -10;
								}
							}
							if(count == 3) {
								clearTriple(hT);
								if(hT.getNth(0) == 1 && hT.getNth(1) == 2 && hT.getNth(2) == 5) {
								//	clearTriple(hT);
									
								}
								
							//	System.out.println("Found triple" + hT.getNth(0) + " " + hT.getNth(1) + " " +hT.getNth(2));
								return;
							}
						}
					}
				}
			}
		}
		//129 12 19
		//three ones two twos two nines
		//12 19 29
		//two ones two twos two nines
	}
	public void clearTriple(HiddenTriple hT) {
		
		for(int b = 0;b < 9;b++) {
			if(cells[b].getValue() == -1 && hT.numContains(cells[b].possibleValues) >= 2) {
				for(int i = 1;i <= 9;i++) {
					if(i != hT.getNth(0) && i != hT.getNth(1) && i != hT.getNth(2)) {
						cells[b].removePossibleValue(i);
					//	System.out.println("Removed: " + i);
					}
				}
			}
		}
		return;
	}
	public void specialNakedTriple() {
		//if(nakedT.size() < 1) {return;}
		//if(nakedD.size() < 1) {return;}
		//TODO THREE DOUBLES <------------------------------------
		//One triple two doubles
		for(int i = 0;i < nakedT.size();i++) {
			for(int j = 0;j < nakedD.size();j++) {
				for(int k = 0;k < nakedD.size();k++) {
					if(j != k) {
						SpecialNakedTriple snt = new SpecialNakedTriple(nakedT.get(i).getFirst(),nakedT.get(i).getSecond(),nakedT.get(i).getThird());
						if(snt.numContains(nakedD.get(j).x,nakedD.get(j).y) == 2) {
							if(snt.numContains(nakedD.get(k).x,nakedD.get(k).y) == 2) {
								for(int b = 0;b < 9;b++) {
									if(cells[b].getValue() != -1) {continue;}
									if(cells[b].possibleValues.size() == 3) {
										if(snt.numContains(cells[b].possibleValues) == 3) {
											continue;
										}

									}
									else if(cells[b].possibleValues.size() == 2) {
										if(snt.numContains(cells[b].possibleValues) == 2) {

											continue;
										}
									}
									for(int m = 0;m < 3;m++) {
										if(cells[b].hasPossibleValue(snt.getNth(m)) == 1) {
								/*			System.out.println("Removed: " + snt.getNth(m) + " from:");
											for(int l = 0;l < cells[b].possibleValues.size();l++) {
												System.out.print(cells[b].possibleValues.get(l)+" ");
											}
											System.out.println();*/
											cells[b].removePossibleValue(snt.getNth(m));

										}
									}
									
								}
								return;
							}
						}
					}
				}
			}
		}
		//Two triples one double
		for(int i = 0;i < nakedT.size();i++) {
			if(nakedT.get(i).getCount() == 2) {
				SpecialNakedTriple snt = new SpecialNakedTriple(nakedT.get(i).getFirst(),nakedT.get(i).getSecond(),nakedT.get(i).getThird());
				for(int j = 0;j < nakedD.size();j++) {
					if(snt.numContains(nakedD.get(j).x,nakedD.get(j).y) == 2) {
						for(int b = 0;b < 9;b++) {
							if(cells[b].getValue() != -1) {continue;}
							if(cells[b].possibleValues.size() == 3) {
								if(snt.numContains(cells[b].possibleValues) == 3) {
									continue;
								}
							}
							else if(cells[b].possibleValues.size() == 2) {
								if(snt.numContains(cells[b].possibleValues) == 2) {

									continue;
								}
							}
							for(int m = 0;m < 3;m++) {
								if(cells[b].hasPossibleValue(snt.getNth(m)) == 1) {
								/*	System.out.println("Removed: " + snt.getNth(m) + " from:");
									for(int l = 0;l < cells[b].possibleValues.size();l++) {
										System.out.print(cells[b].possibleValues.get(l)+" ");
									}
									System.out.println();*/
									cells[b].removePossibleValue(snt.getNth(m));

								}
							}


							

						}
						return;
					}
				}
			}
		}
	}
}
