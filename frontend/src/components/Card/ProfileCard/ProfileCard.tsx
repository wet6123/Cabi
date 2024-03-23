import styled from "styled-components";
import Card, { IButtonProps } from "@/components/Card/Card";
import { ReactComponent as LogoImg } from "@/assets/images/logo.svg";

type ProfileProps = {
  name: string | null;
  button: IButtonProps;
};

const ProfileCard = ({ name, button }: ProfileProps) => {
  return (
    <Card
      title={"프로필"}
      gridArea={"profile"}
      width={"350px"}
      height={"163px"}
      buttons={[button]}
    >
      <ProfileContent>
        <LogoStyled id="topNavLogo" className="cabiButton">
          <CabiLogoStyled>
            <LogoImg className="cabiButton" />
          </CabiLogoStyled>
        </LogoStyled>
        <ProfileDetailWrapper>
          <ProfileDetail>{name}</ProfileDetail>
          <EmailDetail>{name}@student.42seoul.kr</EmailDetail>
        </ProfileDetailWrapper>
      </ProfileContent>
    </Card>
  );
};

const ProfileContent = styled.div`
  display: flex;
  align-items: center;
  flex-direction: row;
  justify-content: flex-start;
  padding: 10px;
  width: 90%;
`;

const LogoStyled = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 60px;
  border-radius: 10px;
  margin-right: 20px;
  background-color: var(--color-background);
`;

const CabiLogoStyled = styled.div`
  width: 45px;
  height: 45px;
  svg {
    .logo_svg__currentPath {
      fill: var(--main-color);
    }
    .logo_svg__top {
      fill: var(--color-background);
    }
    .logo_svg__left {
      fill: var(--color-background);
    }
    .logo_svg__line {
      stroke: var(--color-background);
    }
    .logo_svg__border {
      stroke: var(--color-text-normal);
    }
  }
`;

const ProfileDetailWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  line-height: 1.2;
`;

const ProfileDetail = styled.div`
  padding: 5px 0 0 0;
`;

const EmailDetail = styled(ProfileDetail)`
  font-size: 0.9rem;
  color: var(--gray-tmp-5);
`;

export default ProfileCard;
