
class TestException(Exception):
    msg: str | None = None

    def __init__(self, msg: str) -> None:
        super().__init__()
        self.msg = msg
