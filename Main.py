from typing import List, Tuple, Union, Optional, Dict
import random
from colorama import init
init()
from colorama import Fore, Back, Style

colours = ['White', 'Black']


def cc2a(thing: Union[str, Tuple]):
    """
    If input thing is string, return converted tuple
    elif input thing is tuple, return converted string

    ===Preconditions===
    thing is either string or tuple

    >>>cc2a((0, 8))
    'A1'
    >>>cc2a('A1')
    (0, 8)
    """

    if type(thing) == str:
        letter = ord(thing[0]) - 65
        num = 7 - (int(thing[1]) - 1)
        return letter, num
    else:
        letter = chr(thing[0] + 65)
        num = (-1 * (thing[1] - 8))
        return letter + str(num)


def back_col(pos: Union[str, Tuple[int, int]]) -> str:
    if isinstance(pos, str):
        pos = cc2a(pos)
    if (pos[0] + pos[1]) % 2 == 0:
        return Back.BLACK
    return Back.RESET


def on_board(pos: Tuple[int, int]) -> bool:
    """
    makes sure <pos> is on the the board, no matter colour
    """
    if pos[1] not in range(0, 8) or pos[0] not in range(0, 8):
        return False
    return True


def is_valid(pos: Tuple[int, int], board, colour) -> bool:
    """
    1. Make sure <pos> is on board
    2. Make sure landing on empty tile or enemy piece
    """
    if not on_board(pos):
        return False
    tile = board[pos[0]][pos[1]]
    if not isinstance(tile, Tuple):
        if tile.colour == colour:
            return False
    return True


class GameHasEndedWithTieError(Exception):
    def __str__(self):
        return 'The game has come to an end as at least 1 player is no longer' \
               ' capable of making any moves'


class Game:
    """
    Where all the game-wide operations occur

    ===Attributes===
    board: lis of lists representing the tiles
    pieces: list of peices still alive
    """

    # board: List[List[Piece]]
    # pieces: List[Piece]

    def __init__(self, p1='Person', p2='Comp'):
        """Initializing everything as empty objects"""
        self.pieces = []
        self.discrimainated = {}
        self.tiles = {}
        self.is_checkmate = False
        self.total_moves = 0
        self.lst = [p1, p2]

        '''Initializing everything by calling initializer methods'''
        self.board = self.create_board()
        self.load_pieces()
        self.place_pieces()
        self.fill_dict()

    def play(self):
        while not self.is_checkmate:
            self.check_4_check(self.board)
            player = self.lst[self.total_moves % 2]
            colour = ['white', 'black'][self.total_moves % 2]
            if player != 'Comp':
                print(self)
                print(Style.RESET_ALL + player + '\'s turn. ')
                position = self.get_position(colour)
                piece = self.access(position)
                piece.poss_positions = []
                piece.get_possible_moves(self.board)
                print(Style.RESET_ALL + 'Valid spots to move to:\n' +
                      str(piece.poss_positions))
                pos = input('select the piece you would like to \nmove to or '
                            'type \'exit\' to choose another piece')
                thingy = piece.position
                if pos in piece.poss_positions:
                    pot = self.access(pos)
                    print(pot)
                    self.move_piece(piece, pos, self.board)
                    if colour.lower() == 'white':
                        if self.check_4_check(self.board)[0]:
                            print('You Cant Move Here, This would cause'
                                  ' checkmate')
                            self.move_piece(piece, thingy, self.board)
                            break
                    print(piece, pot)
                    if isinstance(pot, Piece) and pot.colour.lower() == 'black':
                        print(str(pot) + ' removed')
                        loc = cc2a(pot.position)
                        self.board[loc[0]][loc[1]] = piece
                        self.pieces.remove(pot)
                        self.discrimainated['black'].remove(pot)
                        self.place_pieces()
                        assert pot not in self.pieces
                    self.total_moves += 1
                else:
                    print('That wasn\'t valid; Choose a piece again')
            else:
                print('Computer\'s turn')
                poss_pieces = self.discrimainated['black']
                self.comp_get_piece(poss_pieces, self.discrimainated['white'])
        print('CHECKMAAAAAAAAAAAAAATE')

    def __repr__(self):
        blank = Style.RESET_ALL + self.lst[1].center(32) + '\n'
        for row in self.board:
            for tile in row:
                if isinstance(tile, Tuple):
                    blank += back_col(tile) + Fore.RED + Style.BRIGHT + \
                             ' ' + cc2a(tile)
                else:
                    blank += back_col(tile.position) + Style.BRIGHT + ' ' + str(tile)
                blank += ' ' + Style.RESET_ALL
            blank += '\n'
        blank += Fore.RESET + Back.RESET + self.lst[0].center(32)
        return blank

    def load_pieces(self):
        final = {}
        for colour in ['black', 'white']:
            final.setdefault(colour, []).append(King(colour))
            final[colour].append(Queen(colour))
            for i in range(2):
                final[colour].append(Bishop(colour, i))
                final[colour].append(Knight(colour, i))
                final[colour].append(Rook(colour, i))
            for i in range(8):
                final[colour].append(Pawn(colour, i))
        self.pieces.extend(final['black'])
        self.pieces.extend(final['white'])
        self.discrimainated = final

    def create_board(self):
        board = []
        for i in range(8):
            board.append([])
            for j in range(8):
                board[i].append((i, j))
        return board

    def place_pieces(self):
        for piece in self.pieces:
            loc = cc2a(piece.position)
            self.board[loc[0]][loc[1]] = piece

    def check_4_check(self, board) -> Tuple[bool, bool]:
        w_danger, b_danger, danger = False, False, {}
        # getting the kings
        for piece in self.pieces:
            if piece.colour.lower() == 'white' and isinstance(piece, King):
                w_k = piece
            elif piece.colour.lower() == 'black' and isinstance(piece, King):
                b_k = piece
        # are they in danger
        for piece in self.pieces:
            piece.get_possible_moves(board)
            if piece.colour == 'white' and b_k.position in piece.poss_positions:
                b_danger = True
                print(f'{piece} has put Black King in Trouble by being at'
                      f' {piece.position}')
            elif piece.colour == 'black' and w_k.position in \
                    piece.poss_positions:
                w_danger = True
                print(f'{piece} has put White king in Trouble by being at'
                      f'{piece.position}')
        return w_danger, b_danger

    def fill_dict(self):
        for piece in self.pieces:
            self.tiles[str(piece.position)] = piece

    def move_piece(self, piece, new_loc, board):
        """
        Takes New Loc as the CHARNUM form
        """
        curr_loc, new_loc = cc2a(piece.position), cc2a(new_loc)
        if isinstance(new_loc, str):
            new_loc = cc2a(new_loc)
        piece.position = cc2a(new_loc)
        board[curr_loc[0]][curr_loc[1]] = curr_loc
        board[new_loc[0]][new_loc[1]] = piece

    def access(self, pos: str):
        pos = cc2a(pos)
        return self.board[pos[0]][pos[1]]

    def comp_get_piece(self, pieces: List, opposition: List):
        print(self)

        # Creating the grades for each piece####################################

        grades = {}
        for piece in pieces:
            if isinstance(piece, King):
                king = piece
                king.get_possible_moves(self.board)
        for piece in pieces:
            grade = 0
            piece.get_possible_moves(self.board)
            if not piece.poss_positions:
                grade = 0
            else:
                grade += int(len(piece.poss_positions) / 2) + 1
                for opp in opposition:
                    opp.get_possible_moves(self.board)
                    if king.position in opp.poss_positions:
                        if opp.position in piece.poss_positions:
                            grade += 100
                for loc in piece.poss_positions:
                    if isinstance(self.access(loc), Piece):
                        grade += len(lis) - lis.index(type(self.access(loc)))
            if isinstance(piece, King):
                grade -= 1
            grades.setdefault(grade, []).append(piece)

        # Grades have been created##############################################

        if -1 in list(grades.keys()):
            grades.pop(-1)
        if list(grades.keys()) == [0]:
            raise GameHasEndedWithTieError

        # Choose the best piece and the best position###########################

        self.comp_move_piece(grades)
        self.total_moves += 1

    def comp_move_piece(self, grades: Dict):
        """
        Given the Dictionary of Grades, Move a Single Piece
        """
        l_pieces, keys = [], list(grades.keys())
        keys.sort(reverse=True)
        [l_pieces.extend(grades[grade]) for grade in keys]
        the_comp_is_in_danger = self.check_4_check(self.board)[1]
        if the_comp_is_in_danger:
            l_pieces.reverse()
            for piece in l_pieces:
                tup = self.piece_can_get_me_out_of_check(piece)
                if tup[0]:
                    new_loc = random.choice(tup[1])
                    dest = self.access(new_loc)
                    if isinstance(dest, Piece):
                        self.pieces.remove(dest)
                        print(f'{dest} removed by {piece}')
                    self.move_piece(piece, new_loc, self.board)
                    break
            # in the event that no piece was able to get us out of check
            if not tup[0]:
                self.is_checkmate = True
                print('Well Done To You For A Fabulous')

        else:  # the computer is not in danger, make the best piece best move
            lpieces = []
            for grade in keys:
                for i in range(grade):
                    lpieces.extend(grades[grade])  # created weighted list
            lmoves = []  # list of Tuple[Piece, position]
            for piece in l_pieces:
                new_locs = self.get_optimal_move(piece)
                for grade in new_locs:
                    for move in new_locs[grade]:
                        for i in range(grade):
                            temp = (piece, move)
                            lmoves.append(temp)
            if len(lmoves) != 0:
                move = random.choice(lmoves)
                self.move_piece(move[0], move[1], self.board)
                print(f'Moving {move[0]} to {move[1]}')
            else:
                print('GAME OVER. We are at a stalemate')
                self.is_checkmate = True
            # choose randomy from weighted new list

    def piece_can_get_me_out_of_check(self, piece) -> Tuple[bool, List]:
        """
        return True and a list of ways to get out  if you can get out of
            checkmate
        return False otherwise

        === Precondition ===
        The computer is in Check
            i.e. self.check_4_check(self.board)[1] == True
        """
        final = []
        thingy = piece.position
        piece.get_possible_moves(self.board)
        print(piece, piece.position, piece.poss_positions)
        for poss_pos in piece.poss_positions:
            piece.position, init_ = thingy, self.access(poss_pos)
            if isinstance(init_, Piece):  # piece can eat another
                init_pos = init_.position
                self.pieces.remove(init_)
                self.move_piece(piece, poss_pos, self.board)
                if not self.check_4_check(self.board)[1]:  # we got out
                    final.append(poss_pos)
                self.move_piece(piece, thingy, self.board)
                self.insert(init_pos, init_)
            else:
                self.move_piece(piece, poss_pos, self.board)
                if not self.check_4_check(self.board)[1]:  # we got out
                    final.append(poss_pos)
                self.move_piece(piece, thingy, self.board)
        print(final)
        return bool(final), final

    def get_optimal_move(self, piece):
        """
        Given a piece <piece>, return the optimal location for it move to
            without putting it in check
        If the game is at a STALEMATE, return False

        === Preconditions ===
        The computer is not in checkmate
        """
        piece.get_possible_moves(self.board.copy())
        final, thingy = {}, piece.position
        for poss_pos in piece.poss_positions:
            new_loc = self.access(poss_pos)
            self.move_piece(piece, poss_pos, self.board)
            if self.check_4_check(self.board)[1]:  # move would put checkmate us
                grade = 0
            elif isinstance(new_loc, Piece):  # move would let me eat
                grade = len(lis) - lis.index(type(self.access(poss_pos))) + 1
                grade = grade * 2
            else:  # move has no consequence
                grade = 1
            self.move_piece(piece, thingy, self.board)
            if isinstance(new_loc, Piece):
                self.insert(new_loc.position, new_loc)
            final.setdefault(grade, []).append(poss_pos)
        print(piece, final)
        return final

    def get_position(self, colour: str):
        poss = input('Enter the position you would like to move from: ')
        if not isinstance(cc2a(poss), Tuple):
            print('Incorrect input format. Use the form CHARNUM; try again: ')
            poss = self.get_position(colour)
        elif not on_board(cc2a(poss)):
            print('This position is not on board, try again: ')
            poss = self.get_position(colour)
        elif isinstance(self.access(poss), Piece) and\
                self.access(poss).colour != colour:
            print('HANDS OFF!! This piece doesnt belong to you. Try again: ')
            poss = self.get_position(colour)
        elif not isinstance(self.access(poss), Piece):
            print('There is no piece here. Try Again:')
            poss = self.get_position(colour)
        return poss

    def insert(self, position, piece):
        """
        use only in the direst of ocasions
        """
        if isinstance(position, str):
            piece.position = position
            position = cc2a(position)
        else:
            piece.position = cc2a(position)
        self.board[position[0]][position[1]] = piece
        self.pieces.append(piece)
        print(f'Inserting {piece} at {cc2a(position)}')


class Piece:
    """
    this is an abstract class
    used to move
    ===Attributes===
    init_positions: List of poss` initial positions for piece of colour colour
    position: current Position
    poss_positions: list of possible positions
    colour: colour of the piece
    """

    position: str
    init_positions: List[str]
    colour: str
    poss_positions = List[Tuple[int, int]]

    def __init__(self, colour, init_locs, i):
        self.colour = colour
        self.init_pos = init_locs
        self.starting_pos = init_locs[i]
        self.position = init_locs[i]
        self.poss_positions = []

    def get_possible_moves(self, board):
        raise NotImplementedError

    def get_start(self):
        return self.starting_pos


class King(Piece):
    def __init__(self, colour: str):
        if colour.lower() == 'black':
            loc = ['A4']
        else:
            loc = ['H4']
        Piece.__init__(self, colour, loc, 0)

    def __repr__(self):

        if self.colour == 'black':
            return Fore.BLUE + 'BK'
        else:
            return Fore.WHITE + 'WK'

    def get_possible_moves(self, board):
        self.poss_positions = []
        poss_locs, loc = [], cc2a(self.position)
        for row in [-1, 0, 1]:
            for column in [-1, 0, 1]:
                new_loc = (loc[0] + row, loc[1] + column)
                if is_valid(new_loc, board, self.colour) and new_loc != loc:
                    poss_locs.append(cc2a(new_loc))
        self.poss_positions = poss_locs


class Queen(Piece):
    def __init__(self, colour: str):
        if colour.lower() == 'black':
            locs = ['A5']
        else:
            locs = ['H5']
        Piece.__init__(self, colour, locs, 0)

    def __repr__(self):
        if self.colour == 'black':
            return Fore.BLUE + 'BQ'
        else:
            return Fore.WHITE + 'WQ'

    def get_possible_moves(self, board):
        temp, final, loc = [], [], cc2a(self.position)
        #######################
        # #Check For Diagonals##
        #######################
        for j in range(1, 4, 2):
            mult = j - 2
            for i in range(1, 7):
                value = i * mult
                new_loc = (loc[0] + value, loc[1] + value)
                if is_valid(new_loc, board, self.colour):
                    if isinstance(board[new_loc[0]][new_loc[1]], Piece):
                        if board[new_loc[0]][new_loc[1]].colour != self.colour:
                            final.append(cc2a(new_loc))
                            break
                    final.append(cc2a(new_loc))
                else:  # makes sure there nothing in the way
                    break
        for j in range(1, 4, 2):
            mult1, mult2 = j - 2, 2 - j
            for i in range(1, 7):
                new_loc = (loc[0] + mult1 * i, loc[1] + mult2 * i)
                if is_valid(new_loc, board, self.colour):
                    if isinstance(board[new_loc[0]][new_loc[1]], Piece):
                        if board[new_loc[0]][new_loc[1]].colour != self.colour:
                            final.append(cc2a(new_loc))
                            break
                    final.append(cc2a(new_loc))
                else:  # makes sure theres nothing in our way
                    break
        #######################
        # #Check for Straights#
        #######################
        for i in range(1, 4, 2):
            for j in range(1, 8):
                value = j * (i - 2)
                new_loc = (loc[0], loc[1] + value)
                if is_valid(new_loc, board, self.colour):
                    if isinstance(board[new_loc[0]][new_loc[1]], Piece):
                        if board[new_loc[0]][new_loc[1]].colour != self.colour:
                            final.append(cc2a(new_loc))
                            break
                    final.append(cc2a(new_loc))
                else:
                    break
        # going vertically
        for i in range(1, 4, 2):
            for j in range(1, 8):
                value = j * (i - 2)
                new_loc = (loc[0] + value, loc[1])
                if is_valid(new_loc, board, self.colour):
                    if isinstance(board[new_loc[0]][new_loc[1]], Piece):
                        if board[new_loc[0]][new_loc[1]].colour != self.colour:
                            final.append(cc2a(new_loc))
                            break
                    final.append(cc2a(new_loc))
                else:
                    break
        self.poss_positions = final
        return final


class Bishop(Piece):
    def __init__(self, colour: str, i):
        if colour.lower() == 'black':
            locs = ['A6', 'A3']
        else:
            locs = ['H6', 'H3']

        Piece.__init__(self, colour, locs, i)

    def __repr__(self):
        if self.colour.lower() == 'black':
            return Fore.BLUE + 'BB'
        else:
            return Fore.WHITE + 'WB'

    def get_possible_moves(self, board):
        temp, final, loc = [], [], cc2a(self.position)
        for j in range(1, 4, 2):
            mult = j - 2
            for i in range(1, 7):
                value = i * mult
                new_loc = (loc[0] + value, loc[1] + value)
                if is_valid(new_loc, board, self.colour):
                    if isinstance(board[new_loc[0]][new_loc[1]], Piece):
                        if board[new_loc[0]][new_loc[1]].colour != self.colour:
                            final.append(cc2a(new_loc))
                            break
                    final.append(cc2a(new_loc))
                else:  # makes sure there nothing in the way
                    break
        for j in range(1, 4, 2):
            mult1, mult2 = j - 2, 2 - j
            for i in range(1, 7):
                new_loc = (loc[0] + mult1 * i, loc[1] + mult2 * i)
                if is_valid(new_loc, board, self.colour):
                    if isinstance(board[new_loc[0]][new_loc[1]], Piece):
                        if board[new_loc[0]][new_loc[1]].colour != self.colour:
                            final.append(cc2a(new_loc))
                            break
                    final.append(cc2a(new_loc))
                else:  # makes sure theres nothing in our way
                    break
        self.poss_positions = final
        return final


class Knight(Piece):
    def __init__(self, colour: str, i):
        if colour.lower() == 'black':
            locs = ['A7', 'A2']
        else:
            locs = ['H7', 'H2']

        Piece.__init__(self, colour, locs, i)

    def __repr__(self):
        if self.colour == 'black':
            return Fore.BLUE + 'BN'
        else:
            return Fore.WHITE + 'WN'

    def get_possible_moves(self, board):
        loc, final, temp = cc2a(self.position), [], []
        # for the horizontal move of two
        for horizontal in [2, -2]:
            for vertical in [1, -1]:
                new = (loc[0] + vertical, loc[1] + horizontal)
                if is_valid(new, board, self.colour):
                    final.append(cc2a(new))
        # for the vertical move of two
        for horizontal in [1, -1]:
            for vertical in [2, -2]:
                new = (loc[0] + vertical, loc[1] + horizontal)
                if is_valid(new, board, self.colour):
                    final.append(cc2a(new))
        self.poss_positions = final
        return final


class Rook(Piece):
    def __init__(self, colour: str, i):
        if colour.lower() == 'black':
            locs = ['A8', 'A1']
        else:
            locs = ['H8', 'H1']

        Piece.__init__(self, colour, locs, i)

    def __repr__(self):
        if self.colour == 'black':
            return Fore.BLUE + 'BR'
        else:
            return Fore.WHITE + 'WR'

    def get_possible_moves(self, board):
        loc, final = cc2a(self.position), []
        # going horizontally
        for i in range(1, 4, 2):
            for j in range(1, 8):
                value = j * (i - 2)
                new_loc = (loc[0], loc[1] + value)
                if is_valid(new_loc, board, self.colour):
                    if isinstance(board[new_loc[0]][new_loc[1]], Piece):
                        if board[new_loc[0]][new_loc[1]].colour != self.colour:
                            final.append(cc2a(new_loc))
                            break
                    final.append(cc2a(new_loc))
                else:
                    break
        # going vertically
        for i in range(1, 4, 2):
            for j in range(1, 8):
                value = j * (i - 2)
                new_loc = (loc[0] + value, loc[1])
                if is_valid(new_loc, board, self.colour):
                    if isinstance(board[new_loc[0]][new_loc[1]], Piece):
                        if board[new_loc[0]][new_loc[1]].colour != self.colour:
                            final.append(cc2a(new_loc))
                            break
                    final.append(cc2a(new_loc))
                else:
                    break
        self.poss_positions = final
        return final


class Pawn(Piece):
    def __init__(self, colour: str, i):
        if colour.lower() == 'black':
            locs = ['B8', 'B7', 'B6', 'B5', 'B4', 'B3', 'B2', 'B1']
        else:
            locs = ['G8', 'G7', 'G6', 'G5', 'G4', 'G3', 'G2', 'G1']

        print(f'Im coming in at {i}')

        Piece.__init__(self, colour, locs, i)

    def __repr__(self):
        if self.colour == 'black':
            return Fore.BLUE + 'BP'
        else:
            return Fore.WHITE + 'WP'

    def get_possible_moves(self, board):
        loc, poss_moves = cc2a(self.position), []
        if self.colour == 'black':
            if loc[0] != 7:
                down = board[loc[0] + 1][loc[1]]
                if isinstance(down, Tuple):
                    poss_moves.append(cc2a(down))
            if loc[1] != 0:
                seven = board[loc[0] + 1][loc[1] - 1]
                if not isinstance(seven, Tuple) and seven.colour == 'white':
                    poss_moves.append(seven.position)
            if loc[1] != 7:
                five = board[loc[0] + 1][loc[1] + 1]
                if not isinstance(five, Tuple) and five.colour == 'white':
                    poss_moves.append(five.position)
            if self.position == self.get_start():
                down, down2 = board[loc[0] + 1][loc[1]], \
                              board[loc[0] + 2][loc[1]]
                if isinstance(down, Tuple) and isinstance(down2, Tuple):
                    poss_moves.append(cc2a(down2))
        else:
            if loc[0] != 0:
                up = board[loc[0] - 1][loc[1]]
                if isinstance(up, Tuple):
                    poss_moves.append(cc2a(up))
            if loc[1] != 0:
                ten = board[loc[0] - 1][loc[1] - 1]
                if not isinstance(ten, Tuple) and ten.colour == 'black':
                    poss_moves.append(ten.position)
            if loc[1] != 7:

                two = board[loc[0] - 1][loc[1] + 1]
                if not isinstance(two, Tuple) and two.colour == 'black':
                    poss_moves.append(two.position)
            if self.position == self.get_start():
                up, up2 = board[loc[0] - 1][loc[1]], board[loc[0] - 2][loc[1]]
                if isinstance(up, Tuple) and isinstance(up2, Tuple):
                    poss_moves.append(cc2a(up2))
        self.poss_positions = poss_moves
        return poss_moves


lis = [King, Queen, Bishop, Knight, Rook, Pawn]


if __name__ == '__main__':
    game = Game()
    game.play()
