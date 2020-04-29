#include <bits/stdc++.h>
#define all(x) (x).begin(), (x).end()
#define rall(x) (x).rbegin(), (x).rend()
#define F first
#define S second
#define int long long

using namespace std;


/**

SUITS:
0 - Hearts
1 - Boobies
2 - Spades
3 - Clubs

RANKS:
11 - JJ
12 - Q
13 - K
14 - A

COMBINATIONS:
9 - Royal flush     +
8 - Straight flush  +
7 - Four of a kind  +
6 - Full house      +
5 - Flush           +
4 - Straight        +
3 - Three of a kind +
2 - Two pairs       +
1 - One pair        +
0 - High card       +


**/

vector<pair<int, int>> cards;
vector<int> ans;
int comb = 0;


bool is_straight() {
    bool ok = 1, aok;
    for (int i = 2; i <= 10; i++) {
        ok = 1;
        for (int j = i; j < i + 5; j++) {
            aok = 0;
            for (int t = 0; t < 7; t++) {
                if (cards[t].F == j) {
                    aok = 1;
                    break;
                }
            }
            ok = (aok && ok);
        }
        if (ok) return 1;
    }

    ok = 1;
    for (int j = 2; j <= 5; j++) {
        aok = 0;
        for (int t = 0; t < 7; t++) {
            if (cards[t].F == j) {
                aok = 1;
                break;
            }
        }
        ok = (aok && ok);
    }

    aok = 0;
    for (int t = 0; t < 7; t++) {
        if (cards[t].F == 14) {
            aok = 1;
            break;
        }
    }
    ok = (aok && ok);


    return ok;
}

bool is_flush() {
    int hearts = 0, diamonds = 0, spades = 0, clubs = 0;
    for (int i = 0; i < 7; i++) {
        hearts += (cards[i].S == 0);
        diamonds += (cards[i].S == 1);
        spades += (cards[i].S == 2);
        clubs += (cards[i].S == 3);
    }
    return (hearts >= 5 || diamonds >= 5 || spades >= 5 || clubs >= 5);
}

bool is_royal_flush() {
    for (int clr = 0; clr < 4; clr++) {
        bool ok = 1, aok;
        for (int j = 10; j <= 14; j++) {
            aok = 0;
            for (int t = 0; t < 7; t++) {
                if (cards[t].F == j && cards[t].S == clr) {
                    aok = 1;
                    break;
                }
            }
            ok = (aok && ok);
        }
        if (ok) return 1;
    }

    return 0;
}

bool is_straight_flush() {
    bool ok = 1, aok;
    for (int clr = 0; clr < 4; clr++) {
        for (int i = 2; i <= 10; i++) {
            ok = 1;
            for (int j = i; j < i + 5; j++) {
                aok = 0;
                for (int t = 0; t < 7; t++) {
                    if (cards[t].F == j && cards[t].S == clr) {
                        aok = 1;
                        break;
                    }
                }
                ok = (aok && ok);
            }
            if (ok) return 1;
        }
    }

    for (int clr = 0; clr < 4; clr++) {
        ok = 1;
        for (int j = 2; j <= 5; j++) {
            aok = 0;
            for (int t = 0; t < 7; t++) {
                if (cards[t].F == j && cards[t].S == clr) {
                    aok = 1;
                    break;
                }
            }
            ok = (aok && ok);
        }

        aok = 0;
        for (int t = 0; t < 7; t++) {
            if (cards[t].F == 14  && cards[t].S == clr) {
                aok = 1;
                break;
            }
        }
        ok = (aok && ok);

        if (ok) return 1;
    }

    return 0;
}

bool is_four_of_a_kind() {
    bool ok = 1;
    for (int j = 2; j <= 14; j++) {
        int cnt = 0;
        for (int t = 0; t < 7; t++) {
            cnt += (cards[t].F == j);
        }
        if (cnt >= 4) return 1;
    }
    return 0;
}

bool is_three_of_a_kind() {
    bool ok = 1;
    for (int j = 2; j <= 14; j++) {
        int cnt = 0;
        for (int t = 0; t < 7; t++) {
            cnt += (cards[t].F == j);
        }
        if (cnt >= 3) return 1;
    }
    return 0;
}

bool is_two_pairs() {
    bool ok = 1;
    for (int j = 2; j <= 14; j++) {
        int cnt = 0;
        for (int t = 0; t < 7; t++) {
            cnt += (cards[t].F == j);
        }
        if (cnt < 2) continue;

        for (int i = 2; i <= 14; i++) {
            cnt = 0;
            if (i == j) continue;
            for (int t = 0; t < 7; t++) {
                cnt += (cards[t].F == i);
            }
            if (cnt >= 2) return 1;
        }


    }
    return 0;
}

bool is_pair() {
    bool ok = 1;
    for (int j = 2; j <= 14; j++) {
        int cnt = 0;
        for (int t = 0; t < 7; t++) {
            cnt += (cards[t].F == j);
        }
        if (cnt >= 2) return 1;
    }
    return 0;
}

bool is_full_house() {
    bool ok = 1;
    for (int j = 2; j <= 14; j++) {
        int cnt = 0;
        for (int t = 0; t < 7; t++) {
            cnt += (cards[t].F == j);
        }
        if (cnt < 2) continue;

        for (int i = 2; i <= 14; i++) {
            if (i == j) continue;
            cnt = 0;
            for (int t = 0; t < 7; t++) {
                cnt += (cards[t].F == i);
            }
            if (cnt >= 3) return 1;
        }
    }
    return 0;
}


void add_to_full() {
    for (int i = 6; i >= 0; i--) {
        if (ans.size() == 5) return;
        bool is = 0;
        for (auto j: ans) if (j == i) is = 1;
        if (!is) ans.push_back(i);
    }
}

void best_high_card() {
    ans.push_back(6);
    add_to_full();
    return;
}

void best_pair() {
    for (int j = 14; j >= 2; j--) {
        int cnt = 0;
        vector<int> bob;

        for (int t = 0; t < 7; t++) {
            if (bob.size() == 2) break;
            if (cards[t].F != j) continue;
            ++cnt;
            bob.push_back(t);
        }

        if (cnt >= 2) {
            ans = bob;
            add_to_full();
            return;
        }
        bob.clear();
    }
}

void best_two_pairs() {
    for (int j = 14; j >= 2; j--) {
        int cnt = 0;
        vector<int> bob, carl;
        for (int t = 0; t < 7; t++) {
            if (bob.size() == 2) break;
            if (cards[t].F != j) continue;
            ++cnt;
            bob.push_back(t);
        }
        if (cnt < 2) {
            bob.clear();
            continue;
        }

        for (int i = 14; i >= 2; i--) {
            cnt = 0;
            carl.clear();

            if (i == j) continue;
            for (int t = 0; t < 7; t++) {
                if (carl.size() == 2) break;
                if (cards[t].F != i) continue;
                ++cnt;
                carl.push_back(t);
            }
            if (cnt >= 2) {
                for (auto u: bob) ans.push_back(u);
                for (auto u: carl) ans.push_back(u);
                add_to_full();
                return;
            }
        }
    }
}

void best_three_of_a_kind() {
    for (int j = 14; j >= 2; j--) {
        int cnt = 0;
        vector<int> bob;

        for (int t = 0; t < 7; t++) {
            if (bob.size() == 3) break;
            if (cards[t].F != j) continue;
            ++cnt;
            bob.push_back(t);
        }

        if (cnt >= 3) {
            ans = bob;
            add_to_full();
            return;
        }
        bob.clear();
    }
}

void best_full_house() {
    for (int j = 14; j >= 2; j--) {
        int cnt = 0;
        vector<int> bob, carl;
        for (int t = 0; t < 7; t++) {
            if (bob.size() == 3) break;
            if (cards[t].F != j) continue;
            ++cnt;
            bob.push_back(t);
        }
        if (cnt < 3) {
            bob.clear();
            continue;
        }

        for (int i = 14; i >= 2; i--) {
            cnt = 0;
            carl.clear();

            if (i == j) continue;
            for (int t = 0; t < 7; t++) {
                if (carl.size() == 2) break;
                if (cards[t].F != i) continue;
                ++cnt;
                carl.push_back(t);
            }
            if (cnt >= 2) {
                for (auto u: bob) ans.push_back(u);
                for (auto u: carl) ans.push_back(u);
                add_to_full();
                return;
            }
        }
    }
}

void best_flush() {
    int hearts = 0, diamonds = 0, spades = 0, clubs = 0;
    for (int i = 0; i < 7; i++) {
        hearts += (cards[i].S == 0);
        diamonds += (cards[i].S == 1);
        spades += (cards[i].S == 2);
        clubs += (cards[i].S == 3);
    }

    vector<pair<int, int>> pr = {{hearts, 0}, {diamonds, 1}, {spades, 2}, {clubs, 3}};
    sort(all(pr));
    int color = pr[3].S;

    for (int i = 6; i >= 0; i--) {
        if (ans.size() == 5) {
            add_to_full();
            return;
        }
        if (cards[i].S == color) ans.push_back(i);
    }
}

void best_straight() {
    bool ok = 1, aok;
    vector<int> bob;
    for (int i = 10; i >= 2; i--) {
        ok = 1;
        for (int j = i; j < i + 5; j++) {
            aok = 0;
            for (int t = 0; t < 7; t++) {
                if (cards[t].F == j) {
                    aok = 1;
                    bob.push_back(t);
                    break;
                }
            }
            ok = (aok && ok);
        }

        if (ok) {
            ans = bob;
            add_to_full();
            return;
        }

        bob.clear();
    }

    ok = 1;
    for (int j = 2; j <= 5; j++) {
        aok = 0;
        for (int t = 0; t < 7; t++) {
            if (cards[t].F == j) {
                aok = 1;
                bob.push_back(t);
                break;
            }
        }
        ok = (aok && ok);
    }

    aok = 0;
    for (int t = 0; t < 7; t++) {
        if (cards[t].F == 14) {
            aok = 1;
            bob.push_back(t);
            break;
        }
    }

    ans = bob;
    add_to_full();
}

void best_four_of_a_kind() {
    for (int j = 14; j >= 2; j--) {
        int cnt = 0;
        vector<int> bob;

        for (int t = 0; t < 7; t++) {
            if (bob.size() == 4) break;
            if (cards[t].F != j) continue;
            ++cnt;
            bob.push_back(t);
        }

        if (cnt >= 4) {
            ans = bob;
            add_to_full();
            return;
        }
        bob.clear();
    }
}

void best_royal_flush() {
    for (int clr = 0; clr < 4; clr++) {
        bool ok = 1, aok;
        vector<int> bob;
        for (int j = 10; j <= 14; j++) {
            aok = 0;
            for (int t = 0; t < 7; t++) {
                if (cards[t].F == j && cards[t].S == clr) {
                    aok = 1;
                    bob.push_back(t);
                    break;
                }
            }
            ok = (aok && ok);
        }
        if (ok) {
            ans = bob;
            add_to_full();
            return;
        }
        bob.clear();
    }
}

void best_straight_flush() {
    bool ok = 1, aok;
    vector<int> bob;

    for (int clr = 0; clr < 4; clr++) {
        for (int i = 10; i >= 2; i--) {
            ok = 1;
            for (int j = i; j < i + 5; j++) {
                aok = 0;
                for (int t = 0; t < 7; t++) {
                    if (cards[t].F == j && cards[t].S == clr) {
                        aok = 1;
                        break;
                    }
                }
                ok = (aok && ok);
            }
            if (ok) {
                ans = bob;
                add_to_full();
                return;
            }
            bob.clear();
        }
    }

    for (int clr = 0; clr < 4; clr++) {
        ok = 1;
        for (int j = 2; j <= 5; j++) {
            aok = 0;
            for (int t = 0; t < 7; t++) {
                if (cards[t].F == j && cards[t].S == clr) {
                    aok = 1;
                    bob.push_back(t);
                    break;
                }
            }
            ok = (aok && ok);
        }

        aok = 0;
        for (int t = 0; t < 7; t++) {
            if (cards[t].F == 14  && cards[t].S == clr) {
                aok = 1;
                bob.push_back(t);
                break;
            }
        }
        ok = (aok && ok);

        if (ok) {
            ans = bob;
            add_to_full();
            return;
        }

        bob.clear();
    }

}


unsigned long long cool_function() {
    vector<pair<int, int>> pr;
    for (auto i: ans) pr.push_back({cards[i].F, cards[i].S});
    sort(rall(pr));

    unsigned long long cur = comb;
    for (int i = 0; i < 5; i++) {
        cur *= 15LL;
        cur += pr[i].F;
    }
    //cur += 15*15*15*15*15*comb;

    return cur;
}

signed main() {
    for (int i = 0; i < 7; i++) {
        int suit, ranc;
        cin >> ranc >> suit;
        cards.push_back({ranc, suit});
    }

    sort(all(cards));

    if (is_royal_flush()) {
        cout << "ROYAL FLUSH" << endl;
        best_royal_flush();
        comb = 9;
    }
    else if (is_straight_flush()) {
        cout << "STRAIGHT FLUSH" << endl;
        best_straight_flush();
        comb = 8;
    }
    else if (is_four_of_a_kind()) {
        cout << "FOUR OF A KIND" << endl;
        best_four_of_a_kind();
        comb = 7;
    }
    else if (is_full_house()) {
        cout << "FULL HOUSE" << endl;
        best_full_house();
        comb = 6;
    }
    else if (is_flush()) {
        cout << "FLUSH" << endl;
        best_flush();
        comb = 5;
    }
    else if (is_straight()) {
        cout << "STRAIGHT" << endl;
        best_straight();
        comb = 4;
    }
    else if (is_three_of_a_kind()) {
        cout << "THREE OF A KIND" << endl;
        best_three_of_a_kind();
        comb = 3;
    }
    else if (is_two_pairs()) {
        cout << "TWO PAIRS" << endl;
        best_two_pairs();
        comb = 2;
    }
    else if (is_pair()) {
        cout << "PAIR" << endl;
        best_pair();
        comb = 1;
    }
    else {
        cout << "HIGH CARD" << endl;
        best_high_card();
        comb = 0;
    }

    cout << cool_function() << endl;
    for (auto i: ans) cout << cards[i].F << " " << cards[i].S << endl;


    return 0;
}
