package xdb.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

public class ProbTest {

    @Test
    public void testSkillMovesD20() {
        double[] poor = new double[14];
        double[] mixed = new double[14];
        double[] good = new double[14];
        double[] total = new double[14];
        for (int bonus = -1; bonus <= 12; bonus ++) {
            for (int d1 = 1; d1 <= 20; d1++) {
                for (int d2 = 1; d2 <= 20; d2++) {
                    total[bonus+1]++;
                    int die = d1; // < d2 ? d1 : d2;
                    int result = die + bonus;
                    if (die == 1 || result < 11) poor[bonus+1]++;
                    else if (result < 16) mixed[bonus+1]++;
                    else good[bonus+1]++;
                }
            }
        }
        for (int bonus = -1; bonus <= 12; bonus ++) {
            System.out.println(poor[bonus+1]/total[bonus+1]
                + "\t" + mixed[bonus+1]/total[bonus+1]
                + "\t" + good[bonus+1]/total[bonus+1]);
        }
    }

    @Test
    public void testSkillMoves2D10() {
        double[] poor = new double[14];
        double[] mixed = new double[14];
        double[] good = new double[14];
        double[] total = new double[14];
        for (int bonus = -1; bonus <= 12; bonus ++) {
            for (int d1 = 1; d1 <= 10; d1++) {
                for (int d2 = 1; d2 <= 10; d2++) {
                    for (int d3 = 1; d3 <= 10; d3++) {
                        total[bonus+1]++;
                        int die = d1 + d2; // + d3 - min(d1, d2, d3);
                        int result = die + bonus;
                        if (die == 2 || result < 12) poor[bonus+1]++;
                        else if (result < 17) mixed[bonus+1]++;
                        else good[bonus+1]++;
                    }
                }
            }
        }
        for (int bonus = -1; bonus <= 12; bonus ++) {
            System.out.println(poor[bonus+1]/total[bonus+1]
                + "\t" + mixed[bonus+1]/total[bonus+1]
                + "\t" + good[bonus+1]/total[bonus+1]);
        }
    }

	@Test
	public void test() {
		for (int bonus = 0; bonus <= 5; bonus++) {
			double miss = 0;
			double weakHit = 0;
			double strongHit = 0;
			double total = 0;
			for (int die = 1; die <= 6; die++) {
				for (int c1 = 1; c1 <= 10; c1++) {
					for (int c2 = 1; c2 <= 10; c2++) {
						total++;
						int result = die + bonus;
						if (result > 10) result = 10;
						if (result > c1 && result > c2) {
							strongHit++;
						} else if (result > c1 || result > c2) {
							weakHit++;
						} else {
							miss++;
						}
					}
				}
			}
			System.out.println(bonus + "\t" + perc(miss / total) + "\t" + perc(weakHit / total) + "\t" + perc(strongHit / total));
		}
		System.out.println("---");
		for (int bonus = 1; bonus <= 10; bonus++) {
			double miss = 0;
			double weakHit = 0;
			double strongHit = 0;
			double total = 0;
			for (int c1 = 1; c1 <= 10; c1++) {
				for (int c2 = 1; c2 <= 10; c2++) {
					total++;
					int result = bonus;
					if (result > 10) result = 10;
					if (result > c1 && result > c2) {
						strongHit++;
					} else if (result > c1 || result > c2) {
						weakHit++;
					} else {
						miss++;
					}
				}
			}
			System.out.println(bonus + "\t" + perc(miss / total) + "\t" + perc(weakHit / total) + "\t" + perc(strongHit / total));
		}
		for (int negmomentum = 1; negmomentum <= 6; negmomentum++) {
			System.out.println("---");
			System.out.println("negative momentum = -" + negmomentum);
			for (int bonus = 0; bonus <= 5; bonus++) {
				double miss = 0;
				double weakHit = 0;
				double strongHit = 0;
				double total = 0;
				for (int die = 1; die <= 6; die++) {
					for (int c1 = 1; c1 <= 10; c1++) {
						for (int c2 = 1; c2 <= 10; c2++) {
							total++;
							int result = die + bonus;
							if (result > 10) result = 10;
							if (negmomentum == die) {
								result = bonus;
							}
							if (result > c1 && result > c2) {
								strongHit++;
							} else if (result > c1 || result > c2) {
								weakHit++;
							} else {
								miss++;
							}
						}
					}
				}
				System.out.println(bonus + "\t" + perc(miss / total) + "\t" + perc(weakHit / total) + "\t" + perc(strongHit / total));
			}
		}
		for (int posmomentum = 1; posmomentum <= 10; posmomentum++) {
			System.out.println("---");
			System.out.println("positive momentum = " + posmomentum);
			for (int bonus = 0; bonus <= 5; bonus++) {
				double miss = 0;
				double weakHit = 0;
				double strongHit = 0;
				double total = 0;
				double burn = 0;
				for (int die = 1; die <= 6; die++) {
					for (int c1 = 1; c1 <= 10; c1++) {
						for (int c2 = 1; c2 <= 10; c2++) {
							total++;
							int result = die + bonus;
							if (result > 10) result = 10;
							if (result > c1 && result > c2) {
								strongHit++;
							} else if (posmomentum > c1 && posmomentum > c2) {
								strongHit++;
								burn++;
							} else if (result > c1 || result > c2) {
								weakHit++;
							} else if (posmomentum > c1 || posmomentum > c2) {
								weakHit++;
								burn++;
							} else {
								miss++;
							}
						}
					}
				}
				System.out.println(bonus + "\t" + perc(miss / total) + "\t" + perc(weakHit / total) + "\t" + perc(strongHit / total) + "\t" + perc(burn / total));
			}
		}
	}

	public String perc(double value) {
		return new BigDecimal(value * 100).setScale(2, RoundingMode.HALF_UP).toString();
	}

    public int max(int... values) {
        int max = 0;
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public int min(int... values) {
        int min = 1000;
        for (int value : values) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }
}
