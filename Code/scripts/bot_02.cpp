#include <bits/stdc++.h>
#define all(x) (x).begin(), (x).end()
#define rall(x) (x).rbegin(), (x).rend()
#define F first
#define S second

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


// 1)	Если сумма, которую он поставил меньше баланса,
//      повышает на минимальную ставку. Иначе уравнивает.
//
//      Special: - нет


int totalBet, yourBet, balance, blind, special, turn;
vector<pair<int, int>> cards;


bool is_pair() {
    bool ok = 1;
    for (int j = 2; j <= 14; j++) {
        int cnt = 0;
        for (int t = 0; t < cards.size(); t++) {
            cnt += (cards[t].F == j);
        }
        if (cnt >= 2) return 1;
    }
    return 0;
}

bool is_two_pairs() {
    bool ok = 1;
    for (int j = 2; j <= 14; j++) {
        int cnt = 0;
        for (int t = 0; t < cards.size(); t++) {
            cnt += (cards[t].F == j);
        }
        if (cnt < 2) continue;

        for (int i = 2; i <= 14; i++) {
            cnt = 0;
            if (i == j) continue;
            for (int t = 0; t < cards.size(); t++) {
                cnt += (cards[t].F == i);
            }
            if (cnt >= 2) return 1;
        }
    }
    return 0;
}

bool is_three_of_a_kind() {
    for (int j = 2; j <= 14; j++) {
        int cnt = 0;
        for (int t = 0; t < cards.size(); t++) {
            cnt += (cards[t].F == j);
        }
        if (cnt >= 3) return 1;
    }
    return 0;
}

bool is_flush() {
    int hearts = 0, diamonds = 0, spades = 0, clubs = 0;
    for (int i = 0; i < cards.size(); i++) {
        hearts += (cards[i].S == 0);
        diamonds += (cards[i].S == 1);
        spades += (cards[i].S == 2);
        clubs += (cards[i].S == 3);
    }
    return (hearts >= 5 || diamonds >= 5 || spades >= 5 || clubs >= 5);
}

bool is_straight() {
    bool ok = 1, aok;
    int c1 = 0;
    for (int i = 2; i <= 10; i++) {
        for (int j = i; j < i + 5; j++) {
            for (int t = 0; t < cards.size(); t++) {
                if (cards[t].F == j) {
                    c1++;
                    break;
                }
            }
        }
        if (c1 == 5) return 1;
    }
    c1 = 0;
    for (int j = 2; j <= 5; j++) {
        for (int t = 0; t < cards.size(); t++) {
            if (cards[t].F == j) {
                c1++;
                break;
            }
        }
    }
    for (int t = 0; t < cards.size(); t++) {
        if (cards[t].F == 14) {
            c1++;
            break;
        }
    }
    return (c1 == 5);
}



signed main() {
    while (true) {
        int suit, ranc;
        cin >> ranc;
        if (ranc == -1) break;
        cin >> suit;
        cards.push_back({ranc, suit});

    }
    sort(all(cards));
    cin >> totalBet >> yourBet >> balance >> blind >> special >> turn;

    // START

    int k_b = balance/blind, tNum = cards.size() - 2;
    int comb = 0;
    comb = max({is_pair()*1, is_two_pairs()*2, is_three_of_a_kind()*3, is_straight()*4, is_flush()*5, 0});



    if (turn == 0) {
        if (k_b >= 50) {
            if (tNum == 0) {
                if (comb > 0) {
                    time_t rd = time(NULL);
                    if (rd%10 == 0) cout << blind;
                    else cout << 0;
                }
                else cout << 0;
            }
            else {
                if (comb > 3) {
                    time_t rd = time(NULL);
                    if (rd%3 == 0) cout << blind;
                    else cout << 0;
                }
                else if (comb > 1) {
                    if (totalBet == yourBet) {
                        cout << 0;
                        return 0;
                    }
                    time_t rd = time(NULL);
                    if (rd%7 == 0) cout << blind;
                    else cout << 0;
                }
                else  {
                    if (totalBet == yourBet) {
                        cout << 0;
                        return 0;
                    }
                    time_t rd = time(NULL);
                    if (rd%7 == 0) cout << -1;
                    else cout << 0;
                }
            }


        }
        else if (k_b >= 30) {
            if (tNum == 0) {
                if (comb > 0) cout << blind;
                else cout << 0;
            }
            else {
                if (comb > 2) {
                    time_t rd = time(NULL);
                    if (rd%3 == 0) cout << blind;
                    else cout << 0;
                }
                else if (comb > 0) {
                    if (totalBet == yourBet) {
                        cout << 0;
                        return 0;
                    }
                    time_t rd = time(NULL);
                    if (rd%4 == 0) cout << blind;
                    else cout << 0;
                }
                else {
                    if (totalBet == yourBet) {
                        cout << 0;
                        return 0;
                    }
                    cout << -1;
                }
            }
        }
        else if (k_b >= 10) {
            if (tNum == 0) {
                cout << 0;
            }
            else {
                if (comb > 2) {
                    time_t rd = time(NULL);
                    if (rd%4 == 0) cout << blind;
                    else cout << 0;
                }
                else if (comb > 0) cout << 0;
                else {
                    if (totalBet == yourBet) {
                        cout << 0;
                        return 0;
                    }
                    cout << -1;
                }
            }
        }
        else if (k_b > 1) {
            if (tNum == 0) {
                cout << 0;
            }
            else {
                if (comb > 1) cout << 0;
                else {
                    if (totalBet == yourBet) {
                        cout << 0;
                        return 0;
                    }
                    cout << -1;
                }
            }
        }
        else {
            cout << 0;
        }
    }
    else {
        time_t rd = time(NULL);

        if (k_b >= 50) {
            if (comb > 2) {
                if (rd%2 == 0) cout << blind;
                else cout << 0;
            }
            else cout << 0;
        }
        else if (k_b >= 30) {
            if (comb > 2) {
                if (rd%5 == 0) cout << blind;
                else cout << 0;
            }
            else cout << 0;
        }
        else if (k_b >= 10) {
            if (comb > 0) {
                if (rd%6 == 0) cout << blind;
                else cout << 0;
            }
            else {
                if (totalBet == yourBet) {
                    cout << 0;
                    return 0;
                }
                cout << -1;
            }
        }
        else if (k_b > 1) {
            if (comb > 0) {
                cout << 0;
            }
            else {
                if (totalBet == yourBet) {
                    cout << 0;
                    return 0;
                }
                cout << -1;
            }
        }
        else cout << 0;

    }

    // END


    return 0;
}
