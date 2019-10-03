package com.izayacity.algorithms.backtracking;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CreatedBy:   Francis Xirui Yang
 * Date:        9/23/19
 * mailto:      izayacity@gmail.com
 * version:     1.0 since 1.0
 */
public class RemoveParenthesis {

    private Set<String> validExpressions = new HashSet<String>();
    private Gson gson = new Gson();

    private void recurse(String s, int index, int leftCount, int rightCount, int leftRemains, int rightRemains, StringBuilder expression) {
        if (index == s.length()) {
            if (leftRemains == 0 && rightRemains == 0) {
                this.validExpressions.add(expression.toString());
            }
            return;
        }
        char character = s.charAt(index);
        int length = expression.length();

        // The discard case where we delete the character. Note that here we have our pruning condition.
        // We don't recurse if the remaining count for that parenthesis is == 0.
        if ((character == '(' && leftRemains > 0) || (character == ')' && rightRemains > 0)) {
            this.recurse(s, index + 1, leftCount, rightCount, leftRemains - (character == '(' ? 1 : 0), rightRemains - (character == ')' ? 1 : 0), expression);
        }
        expression.append(character);

        // Simply recurse one step further if the current character is not a parenthesis.
        if (character != '(' && character != ')') {
            this.recurse(s, index + 1, leftCount, rightCount, leftRemains, rightRemains, expression);
        } else if (character == '(') {
            // Consider an opening bracket.
            this.recurse(s, index + 1, leftCount + 1, rightCount, leftRemains, rightRemains, expression);
        } else if (rightCount < leftCount) {
            // Consider a closing bracket.
            this.recurse(s, index + 1, leftCount, rightCount + 1, leftRemains, rightRemains, expression);
        }
        // Delete for backtracking.
        expression.deleteCharAt(length);
    }

    public List<String> removeInvalidParentheses(String s) {
        int left = 0, right = 0;
        // First, we find out the number of misplaced left and right parentheses.
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else if (s.charAt(i) == ')') {
                right++;
            }
        }
        if (left < right) {
            right -= left;
            left = 0;
        } else {
            left -= right;
            right = 0;
        }
        this.recurse(s, 0, 0, 0, left, right, new StringBuilder());
        return new ArrayList<String>(this.validExpressions);
    }

    public static void main(String[] args) {
        String str = "()())()";
        RemoveParenthesis solution = new RemoveParenthesis();
        List<String> result = solution.removeInvalidParentheses(str);
        System.out.println(solution.gson.toJson(result));
    }
}
